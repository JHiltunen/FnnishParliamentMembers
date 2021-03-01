package com.jhiltunen.finnishparliamentmembers.logic

import android.util.Log
import androidx.lifecycle.LiveData
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember

class ParliamentRepository(private val parliamentDao: ParliamentDao) {
    val parliamentData: LiveData<List<ParliamentMember>> = parliamentDao.getAllMembers()

    suspend fun insertParliamentMember(parliamentMember: ParliamentMember) {
        Log.d("REPO", parliamentMember.toString())
        parliamentDao.insert(parliamentMember)
    }

    suspend fun updateParliamentMember(parliamentMember: ParliamentMember) {
        parliamentDao.insert(parliamentMember)
    }

    fun getAllMembers():LiveData<List<ParliamentMember>> {
        return parliamentDao.getAllMembers()
    }
}