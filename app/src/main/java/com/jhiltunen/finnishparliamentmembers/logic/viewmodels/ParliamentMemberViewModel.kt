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


}