package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.jhiltunen.finnishparliamentmembers.database.MemberLikes
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParliamentMemberDetailsViewModel(private var hetekaId: Int, application: Application): AndroidViewModel(application) {

    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    var member : LiveData<ParliamentMember>
    var memberLikes : LiveData<Int>

    init {
        member = parliamentRepository.getMember(hetekaId)
        memberLikes = parliamentRepository.getMemberLikes(hetekaId)
    }

    fun increaseLikes() {
        Log.d("PARLIAMENTMEMBERDETAILSVIEWMODEL", memberLikes.value.toString())
        viewModelScope.launch(Dispatchers.IO) {
            memberLikes.value?.plus(1)?.let { MemberLikes(hetekaId, it) }?.let { updateMemberLikes(it) }
        }
    }

    fun decreaseLikes() {
        viewModelScope.launch(Dispatchers.IO) {
            updateMemberLikes(MemberLikes(hetekaId, memberLikes.value!!.minus(1)))
        }
    }

    fun updateMemberLikes(memberLikes: MemberLikes) {
        viewModelScope.launch(Dispatchers.IO) {
            parliamentRepository.updateMemberLikes(memberLikes)
        }
    }
}