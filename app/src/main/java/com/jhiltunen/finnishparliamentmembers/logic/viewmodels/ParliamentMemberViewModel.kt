package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository
import com.jhiltunen.finnishparliamentmembers.logic.services.FetchApiWork
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.concurrent.TimeUnit

class ParliamentMemberViewModel(application: Application): AndroidViewModel(application) {
        private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    /*init {
        Log.d("***", "INIT, setup recurringwork")
        setupRecurringWork()
    }*/

    /*fun insertMemberToDatabase(member: ParliamentMember) {
        //Log.d("INSERT" , parliamentMembers.value!![0].firstname)
        viewModelScope.launch { parliamentRepository.insertParliamentMember(member) }
    }*/

    /*val fetchRequest =
        PeriodicWorkRequestBuilder<FetchApiWork>(1, TimeUnit.HOURS)
            // Additional configuration
            .build()
*/
    private fun setupRecurringWork() {
        Log.d("***", "settingUpRecurringWork...")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(false)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<FetchApiWork>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this.getApplication()).enqueueUniquePeriodicWork(
            FetchApiWork.WORK_FETCH,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

    fun refreshParliamentMemberInfo() {
        viewModelScope.launch {
            try {
                parliamentRepository.fetchParliamentInfoFromApi()
            } catch (e: IOException) {
                Log.d("ERROR", e.toString())
            }
        }
    }
}