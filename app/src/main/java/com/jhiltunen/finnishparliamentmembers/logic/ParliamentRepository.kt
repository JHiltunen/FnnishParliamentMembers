package com.jhiltunen.finnishparliamentmembers.logic

import android.util.Log
import androidx.lifecycle.LiveData
import com.jhiltunen.finnishparliamentmembers.database.MemberLikes
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import com.jhiltunen.finnishparliamentmembers.logic.services.ParliamentMemberApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ParliamentRepository(private val parliamentDao: ParliamentDao) {

    suspend fun insertAllParliamentMembers(members: List<ParliamentMember>) {
        parliamentDao.insertAll(members)
    }

    suspend fun insertDefaultLikes(members: List<ParliamentMember>) {
        var memberLikes : MutableList<MemberLikes> = ArrayList()
        for (member in members) {
            memberLikes.add(MemberLikes(member.hetekaId, 0))
        }

        parliamentDao.insertDefaultLikes(memberLikes)
    }

    fun getMember(hetekaId: Int):LiveData<ParliamentMember> {
        return parliamentDao.getMember(hetekaId)
    }

    suspend fun updateAllMembers(members: List<ParliamentMember>) {
        parliamentDao.updateAllMembers(members)
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

    private fun getMembersRowCount(): Int {
        return parliamentDao.getMembersRowCount()
    }

    suspend fun fetchParliamentInfoFromApi() {
        val parliamentMembers = ParliamentMemberApi.retrofitService.getParliamentMembers()
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("TESTCASE", "ROW COUNT:${getMembersRowCount()}")
            if (getMembersRowCount() > 0) {
                updateAllMembers(parliamentMembers)
                Log.d("TESTCASE", "Data was already in database")
            } else {
                Log.d("TESTCASE", "Data wasn't in database")
                insertAllParliamentMembers(parliamentMembers)
                insertDefaultLikes(parliamentMembers)
            }
        }
    }
}