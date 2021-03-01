package com.jhiltunen.finnishparliamentmembers.logic

import android.util.Log
import androidx.lifecycle.LiveData
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabaseDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember

class ParliamentRepository(private val parliamentDatabaseDao: ParliamentDatabaseDao) {
    val parliamentData: LiveData<List<ParliamentMember>> = parliamentDatabaseDao.getAllMembers()

    suspend fun insertParliamentMember(parliamentMember: ParliamentMember) {
        Log.d("REPO", parliamentMember.toString())
        parliamentDatabaseDao.insert(parliamentMember)
    }

    suspend fun updateParliamentMember(parliamentMember: ParliamentMember) {
        parliamentDatabaseDao.insert(parliamentMember)
    }

    fun getAllMembers():LiveData<List<ParliamentMember>> {
        return parliamentDatabaseDao.getAllMembers()
    }
}