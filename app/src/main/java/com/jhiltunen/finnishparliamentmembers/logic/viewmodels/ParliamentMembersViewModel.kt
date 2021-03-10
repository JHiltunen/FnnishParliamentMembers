package com.jhiltunen.finnishparliamentmembers.logic.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.ParliamentRepository

/**
 * ViewModel for ParliamentMembersFragment.
 *
 * @param party The name of the specific party that user has clicked on previous view.
 */
class ParliamentMembersViewModel(party: String, application: Application): AndroidViewModel(application) {
    // LiveData of members tha belong to given party
    val parliamentMembers: LiveData<List<ParliamentMember>>

    private val _navigateToMemberDetail = MutableLiveData<Int?>()
    val navigateToMemberDetail
        get() = _navigateToMemberDetail

    private val parliamentDao: ParliamentDao = ParliamentDatabase.getInstance(application).parliamentDao()
    private val parliamentRepository: ParliamentRepository = ParliamentRepository(parliamentDao)

    /**
     * Initialize and fetch all members belonging to selected party from database to LiveData
     */
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