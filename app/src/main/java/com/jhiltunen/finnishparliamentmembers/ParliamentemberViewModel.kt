package com.jhiltunen.finnishparliamentmembers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ParliamentMemberViewModel: ViewModel() {
    private val _parliamentMembers = MutableLiveData<List<ParliamentMember>>()
    val parliamentMembers:LiveData<List<ParliamentMember>>
        get() =_parliamentMembers

    init {
        getParliamentMembers()
    }

    fun getParliamentMembers() {
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