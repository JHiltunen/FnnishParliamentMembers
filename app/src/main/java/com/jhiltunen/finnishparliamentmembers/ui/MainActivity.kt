package com.jhiltunen.finnishparliamentmembers.ui

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.jhiltunen.finnishparliamentmembers.R
import com.jhiltunen.finnishparliamentmembers.databinding.ActivityMainBinding
import com.jhiltunen.finnishparliamentmembers.logic.services.FetchApiWork
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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