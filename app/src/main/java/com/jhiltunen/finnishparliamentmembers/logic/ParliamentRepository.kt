package com.jhiltunen.finnishparliamentmembers.logic

import android.util.Log
import androidx.lifecycle.LiveData
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
    }

    suspend fun updateParliamentMember(parliamentMember: ParliamentMember) {
        parliamentDao.insert(parliamentMember)
    }

    fun getAllMembers():LiveData<List<ParliamentMember>> {
        return parliamentDao.getAllMembers()
    }

    suspend fun fetchParliamentInfoFromApi() {
        withContext(Dispatchers.IO) {
            insertAllParliamentMembers(ParliamentMemberApi.retrofitService.getParliamentMembers())
        }
    }
}