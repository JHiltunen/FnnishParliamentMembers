package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jhiltunen.finnishparliamentmembers.logic.models.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.services.ParliamentMemberApi
import kotlinx.coroutines.launch

class ParliamentMemberViewModel: ViewModel() {
    private val _parliamentMembers = MutableLiveData<List<ParliamentMember>>()
    val parliamentMembers:LiveData<List<ParliamentMember>>
        get() =_parliamentMembers

    /*init {
        getParliamentMembers()
    }*/

    private fun getParliamentMembers() {
        viewModelScope.launch {
            try {
                _parliamentMembers.value = ParliamentMemberApi.retrofitService.getParliamentMembers()
             } catch (e: Exception) {
                Log.d("***", "getParliamentMembers: ${e.toString()}")
                _parliamentMembers.value = ArrayList()
            }
        }
    }

}