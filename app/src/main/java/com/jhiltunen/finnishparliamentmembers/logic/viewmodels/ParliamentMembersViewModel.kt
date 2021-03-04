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

    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    init {
        parliamentMembers = parliamentRepository.getAllMembers()
       // Log.d("***", "INIT, setup recurringwork")
     //   setupRecurringWork()
    }

    /*private fun getParliamentMembersFromApi() {
        viewModelScope.launch {
            try {
                _parliamentMembers.value = ParliamentMemberApi.retrofitService.getParliamentMembers()
            } catch (e: Exception) {
                Log.d("***", "getParliamentMembers: ${e.toString()}")
                _parliamentMembers.value = ArrayList()
            }
        }
    }
*/
    fun insertMemberToDatabase(member: ParliamentMember) {
        Log.d("***" , parliamentMembers.value!![0].firstname)
        viewModelScope.launch { parliamentRepository.insertParliamentMember(member) }
    }

    fun insertAllMembersToDatabase(members: List<ParliamentMember>) {
        viewModelScope.launch {
            parliamentRepository.insertAllParliamentMembers(members)
        }
    }
}