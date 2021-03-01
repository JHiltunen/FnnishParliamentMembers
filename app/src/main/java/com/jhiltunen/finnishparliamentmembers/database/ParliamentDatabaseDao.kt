package com.jhiltunen.finnishparliamentmembers.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParliamentDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(member: ParliamentMember)

    @Update
    suspend fun update(member: ParliamentMember)

    @Query("SELECT * FROM members")
    fun getAllMembers(): LiveData<List<ParliamentMember>>

    @Query("SELECT * FROM members WHERE heteka_id = :hetekaId")
    fun getMember(hetekaId: Int): ParliamentMember?
}