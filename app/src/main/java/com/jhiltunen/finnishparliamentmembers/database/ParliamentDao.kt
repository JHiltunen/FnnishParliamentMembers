package com.jhiltunen.finnishparliamentmembers.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ParliamentDao {

    @Insert
    fun insert(member: ParliamentMember)

    @Update
    fun update(member: ParliamentMember)

    @Query("SELECT * FROM members")
    fun getAllMembers(): LiveData<List<ParliamentMember>>
}