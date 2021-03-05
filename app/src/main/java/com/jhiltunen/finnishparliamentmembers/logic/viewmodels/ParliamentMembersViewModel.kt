package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository
import kotlinx.coroutines.launch

class ParliamentMembersViewModel(application: Application): AndroidViewModel(application) {
    val parliamentMembers: LiveData<List<ParliamentMember>>

    private val _navigateToMemberDetail = MutableLiveData<Int?>()
    val navigateToMemberDetail
        get() = _navigateToMemberDetail

    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    init {
        parliamentMembers = parliamentRepository.getAllMembers()
    }

    fun insertMemberToDatabase(member: ParliamentMember) {
        Log.d("***" , parliamentMembers.value!![0].firstname)
        viewModelScope.launch { parliamentRepository.insertParliamentMember(member) }
    }

    fun insertAllMembersToDatabase(members: List<ParliamentMember>) {
        viewModelScope.launch {
            parliamentRepository.insertAllParliamentMembers(members)
        }
    }

    fun onParliamentMemberClicked(id: Int) {
        _navigateToMemberDetail.value = id
    }

    fun onParliamentMemberNavigated() {
        _navigateToMemberDetail.value = null
    }
}