package com.jhiltunen.finnishparliamentmembers.logic.services

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.work.*
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository

class FetchApiWork(appContext: Context, workerParams: WorkerParameters) : CoroutineWorker(appContext, workerParams) {
    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(appContext).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    private val _parliamentMembers = MutableLiveData<List<ParliamentMember>>()

    companion object {
        const val WORK_FETCH = "com.jhiltunen.logic.services.FetchApiWork"
    }

    override suspend fun doWork(): Result {
        // Do work here
        try {
            Log.d("fetch", "Fetching data....")
            parliamentRepository.fetchParliamentInfoFromApi()
        } catch (e: Exception) {
            Log.d("fetch", "getParliamentMembers: ${e.toString()}")
            _parliamentMembers.value = ArrayList()
            return Result.failure()
        }

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}
