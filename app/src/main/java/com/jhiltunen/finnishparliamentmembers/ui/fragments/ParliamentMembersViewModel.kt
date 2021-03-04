package com.jhiltunen.finnishparliamentmembers.ui.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository
import com.jhiltunen.finnishparliamentmembers.logic.services.ParliamentMemberApi
import kotlinx.coroutines.launch

class ParliamentMembersViewModel(application: Application): AndroidViewModel(application) {
    private val _parliamentMembers = MutableLiveData<List<ParliamentMember>>()
    val parliamentMembers: LiveData<List<ParliamentMember>>
        get() =_parliamentMembers

    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    init {
        getParliamentMembersFromApi()
    }

    private fun getParliamentMembersFromApi() {
        viewModelScope.launch {
            try {
                _parliamentMembers.value = ParliamentMemberApi.retrofitService.getParliamentMembers()
            } catch (e: Exception) {
                Log.d("***", "getParliamentMembers: ${e.toString()}")
                _parliamentMembers.value = ArrayList()
            }
        }
    }

    fun insertMemberToDatabase(member: ParliamentMember) {
        //Log.d("INSERT" , parliamentMembers.value!![0].firstname)
        viewModelScope.launch { parliamentRepository.insertParliamentMember(member) }
    }
}