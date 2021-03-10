package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository

class ParliamentMembersViewModel(party: String, application: Application): AndroidViewModel(application) {
    val parliamentMembers: LiveData<List<ParliamentMember>>

    private val _navigateToMemberDetail = MutableLiveData<Int?>()
    val navigateToMemberDetail
        get() = _navigateToMemberDetail

    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    init {
        parliamentMembers = parliamentRepository.getAllMembersInParty(party)
    }

    fun onParliamentMemberClicked(id: Int) {
        _navigateToMemberDetail.value = id
    }

    fun onParliamentMemberNavigated() {
        _navigateToMemberDetail.value = null
    }
}