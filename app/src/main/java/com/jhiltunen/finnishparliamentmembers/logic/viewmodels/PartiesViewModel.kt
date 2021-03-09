package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository

class PartiesViewModel (application: Application): AndroidViewModel(application) {
    val parties: LiveData<List<String>>

    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    init {
        parties = parliamentRepository.getAllParties()
    }

}