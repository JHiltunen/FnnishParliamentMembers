package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository

class ParliamentMemberDetailsViewModel(hetekaId: Int, application: Application): AndroidViewModel(application) {

    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    var member : LiveData<ParliamentMember>
    init {
        member = parliamentRepository.getMember(hetekaId)
    }
}