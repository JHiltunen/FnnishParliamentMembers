package com.jhiltunen.finnishparliamentmembers.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.databinding.ActivityMainBinding
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository
import com.jhiltunen.finnishparliamentmembers.logic.services.FetchApiWork
import com.jhiltunen.finnishparliamentmembers.logic.services.ParliamentMemberApiService
import kotlinx.coroutines.CoroutineScope
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: ParliamentRepository
    private lateinit var dao: ParliamentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        dao = ParliamentDatabase.getInstance(applicationContext).parliamentDao()
        repository = ParliamentRepository(dao)

        suspend {
            repository.fetchParliamentInfoFromApi()
        }

        // constraints for WorkManager
        val constraints = Constraints.Builder()
            .apply {

            }
            .build()

        // request that's executed every 15 minutes
        val repeatingRequest = PeriodicWorkRequestBuilder<FetchApiWork>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        // create WorkManager
        val workManager = WorkManager.getInstance(application)
        // add the repeatingRequest to the enqueue
        workManager.enqueue(repeatingRequest)
        // observe the workManager if it's status changes
        workManager.getWorkInfoByIdLiveData(repeatingRequest.id).observe(this, Observer {
            if (it != null) {
                d("***", "Status changed to ${it.state.isFinished}")
            }
        })
    }
}