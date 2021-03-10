package com.jhiltunen.finnishparliamentmembers.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

/**
 * Defines SQL commands for the app to use for database.
 * Runs queries against Room database.
 */
@Dao
interface ParliamentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(member: ParliamentMember)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(members: List<ParliamentMember>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDefaultLikes(memberLikes: List<MemberLikes>)

    @Update
    suspend fun update(member: ParliamentMember)

    @Query("SELECT * FROM members")
    fun getAllMembers(): LiveData<List<ParliamentMember>>

    @Query("SELECT * FROM members WHERE heteka_id = :hetekaId")
    fun getMember(hetekaId: Int): LiveData<ParliamentMember>

    @Query("SELECT DISTINCT partyName FROM members")
    fun getAllParties(): LiveData<List<String>>

    @Query("SELECT * FROM members WHERE partyName = :party")
    fun getAllMembersInParty(party: String): LiveData<List<ParliamentMember>>

    @Query("SELECT likes FROM members_likes WHERE heteka_id = :hetekaId")
    fun getMembersLikes(hetekaId: Int): LiveData<Int>

    @Update
    fun updateMemberLikes(memberLikes: MemberLikes)

}