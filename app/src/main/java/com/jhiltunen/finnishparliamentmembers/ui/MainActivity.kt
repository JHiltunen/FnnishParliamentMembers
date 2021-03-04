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
import com.jhiltunen.finnishparliamentmembers.databinding.ActivityMainBinding
import com.jhiltunen.finnishparliamentmembers.logic.services.FetchApiWork
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val constraints = Constraints.Builder()
            .apply {

            }
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<FetchApiWork>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        val workManager = WorkManager.getInstance(application)
        workManager.enqueue(repeatingRequest)

        workManager.getWorkInfoByIdLiveData(repeatingRequest.id).observe(this, Observer {
            if (it != null) {
                d("***", "Status changed to ${it.state.isFinished}")
            }
        })
    }

    private fun setupRecurringWork() {
        Log.d("***", "settingUpRecurringWork...")

    }
}