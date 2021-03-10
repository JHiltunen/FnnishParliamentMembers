package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.jhiltunen.finnishparliamentmembers.database.MemberLikes
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for MemberDetailsFragment.
 *
 * @param hetekaId The id of the specific member whose info we are working on.
 */
class ParliamentMemberDetailsViewModel(private var hetekaId: Int, application: Application): AndroidViewModel(application) {
    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    // LiveData of member details and member likes. If data changes on database it will be shown on UI.
    var member : LiveData<ParliamentMember>
    var memberLikes : LiveData<Int>

    /**
     * Initialize and fetch member and member likes data from database to LiveData
     */
    init {
        member = parliamentRepository.getMember(hetekaId)
        memberLikes = parliamentRepository.getMemberLikes(hetekaId)
    }

    /**
     * Function to increase members likes. Executed when thumb up button is pressed.
     */
    fun increaseLikes() {
        Log.d("PARLIAMENTMEMBERDETAILSVIEWMODEL", memberLikes.value.toString())
        viewModelScope.launch(Dispatchers.IO) {
            memberLikes.value?.plus(1)?.let { MemberLikes(hetekaId, it) }?.let { updateMemberLikes(it) }
        }
    }

    /**
     * Function to decrease member likes. Executed when thumb down button is pressed.
     */
    fun decreaseLikes() {
        viewModelScope.launch(Dispatchers.IO) {
            updateMemberLikes(MemberLikes(hetekaId, memberLikes.value!!.minus(1)))
        }
    }

    /**
     * Function to update members changed likes to database.
     */
    private fun updateMemberLikes(memberLikes: MemberLikes) {
        // Prevent running database queries on Main Thread
        viewModelScope.launch(Dispatchers.IO) {
            parliamentRepository.updateMemberLikes(memberLikes)
        }
    }
}