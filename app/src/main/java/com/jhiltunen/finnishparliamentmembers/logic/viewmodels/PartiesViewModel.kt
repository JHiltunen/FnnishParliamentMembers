package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for PartiesFragment.
 */
class PartiesViewModel (application: Application): AndroidViewModel(application) {
    // LiveData of Finnish Parliament parties
    var parties: LiveData<List<String>>

    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    /**
     * Initialize and fetch all parties from database to LiveData
     */
    init {
        parties = parliamentRepository.getAllParties()
    }

    /**
     * Function that fetches data from Eduskunta API
     */
    fun fetchDataFromApi() {
        // Prevent running on Main Thread
        viewModelScope.launch {
            parliamentRepository.fetchParliamentInfoFromApi()
            parties = parliamentRepository.getAllParties()
        }
    }
}