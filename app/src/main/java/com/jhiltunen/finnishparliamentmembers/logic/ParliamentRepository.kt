package com.jhiltunen.finnishparliamentmembers.logic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jhiltunen.finnishparliamentmembers.database.MemberLikes
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.services.ParliamentMemberApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParliamentRepository(private val parliamentDao: ParliamentDao) {
    val parliamentData: LiveData<List<ParliamentMember>> = parliamentDao.getAllMembers()

    suspend fun insertParliamentMember(parliamentMember: ParliamentMember) {
        Log.d("REPO", parliamentMember.toString())
        parliamentDao.insert(parliamentMember)
    }

    suspend fun insertAllParliamentMembers(members: List<ParliamentMember>) {
        parliamentDao.insertAll(members)
        insertDefaultLikes(members)
    }

    suspend fun insertDefaultLikes(members: List<ParliamentMember>) {
        var memberLikes : MutableList<MemberLikes> = ArrayList()
        for (member in members) {
            memberLikes.add(MemberLikes(member.hetekaId, 0))
        }

        parliamentDao.insertDefaultLikes(memberLikes)
    }

    suspend fun updateParliamentMember(parliamentMember: ParliamentMember) {
        parliamentDao.insert(parliamentMember)
    }

    fun getAllMembers():LiveData<List<ParliamentMember>> {
        return parliamentDao.getAllMembers()
    }

    fun getMember(hetekaId: Int):LiveData<ParliamentMember> {
        return parliamentDao.getMember(hetekaId)
    }

    fun getAllParties():LiveData<List<String>> {
        return parliamentDao.getAllParties()
    }

    fun getAllMembersInParty(party: String): LiveData<List<ParliamentMember>> {
        return parliamentDao.getAllMembersInParty(party)
    }

    fun getMemberLikes(hetekaId: Int): LiveData<Int> {
        return parliamentDao.getMembersLikes(hetekaId)
    }

    fun updateMemberLikes(memberLikes: MemberLikes) {
        parliamentDao.updateMemberLikes(memberLikes)
    }

    suspend fun fetchParliamentInfoFromApi() {
        withContext(Dispatchers.IO) {
            var membersFromApi = ParliamentMemberApi.retrofitService.getParliamentMembers()
            var membersFromDatabase = getAllMembers()

            if (membersFromApi != membersFromDatabase.value) {
                insertAllParliamentMembers(ParliamentMemberApi.retrofitService.getParliamentMembers())
            } else {
                Log.d("NOTE", "Data is already in database")
            }
        }
    }
}