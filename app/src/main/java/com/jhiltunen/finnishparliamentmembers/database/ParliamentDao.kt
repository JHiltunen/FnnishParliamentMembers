package com.jhiltunen.finnishparliamentmembers.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ParliamentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(member: ParliamentMember)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(members: List<ParliamentMember>)

    @Update
    suspend fun update(member: ParliamentMember)

    @Query("SELECT * FROM members")
    fun getAllMembers(): LiveData<List<ParliamentMember>>

    @Query("SELECT * FROM members WHERE heteka_id = :hetekaId")
    fun getMember(hetekaId: Int): ParliamentMember?
}