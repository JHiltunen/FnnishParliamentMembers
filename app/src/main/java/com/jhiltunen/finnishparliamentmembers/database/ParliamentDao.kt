package com.jhiltunen.finnishparliamentmembers.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Defines SQL commands for the app to use for database.
 * Runs queries against Room database.
 */
@Dao
interface ParliamentDao {
    /**
     * Function to insert list containing all ParliamentMember objects to database.
     * On conflict data will be replaced.
     * @param members List of all ParliamentMember objects
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(members: List<ParliamentMember>)

    /**
     * Function to insert list containing all MemberLikes objects to database.
     * On conflict data will be replaced.
     * @param memberLikes List of all MemberLikes objects
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDefaultLikes(memberLikes: List<MemberLikes>)

    /**
     * Function to update all ParliamentMember objects information to database.
     * @param members List of ParliamentMember objects that need to be updated.
     */
    @Update
    suspend fun updateAllMembers(members: List<ParliamentMember>)

    /**
     * Function to get ParliamentMember data with given hetekaId
     * @param hetekaId Id of the member whose data will be returned
     * @return Returns member with specific id as LiveData<ParliamentMember>.
     */
    @Query("SELECT * FROM members WHERE heteka_id = :hetekaId")
    fun getMember(hetekaId: Int): LiveData<ParliamentMember>

    /**
     * Function to get list of all Finnish Parliament parties name.
     * @return Returns list of all parties names in  Finnish parliament as LiveData<List<String>>.
     */
    @Query("SELECT DISTINCT partyName FROM members")
    fun getAllParties(): LiveData<List<String>>

    /**
     * Function to get all members that belong to specified party.
     * @param party Party that member has to belong..
     * @return Returns All members that belongs to given party as LiveData<List<ParliamentMember>>.
     */
    @Query("SELECT * FROM members WHERE partyName = :party")
    fun getAllMembersInParty(party: String): LiveData<List<ParliamentMember>>

    /**
     * Function to get likes that specific member has.
     * @param hetekaId Id of the member whose likes will be returned.
     * @return Returns number of likes for specific member as LiveData<Int>.
     */
    @Query("SELECT likes FROM members_likes WHERE heteka_id = :hetekaId")
    fun getMembersLikes(hetekaId: Int): LiveData<Int>

    /**
     * Function to get number of rows in members table.
     * @return Returns the number of rows as Int.
     */
    @Query("SELECT COUNT(members.heteka_id) FROM members")
    fun getMembersRowCount(): Int

    /**
     * Function to update MemberLikes objects information to database.
     */
    @Update
    fun updateMemberLikes(memberLikes: MemberLikes)

}