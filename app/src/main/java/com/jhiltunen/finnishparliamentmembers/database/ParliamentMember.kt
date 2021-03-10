package com.jhiltunen.finnishparliamentmembers.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents room database table "members".
 * @param id Is unique id for specific parliament member.
 * @param seatNumber Parliament member seat number.
 * @param lastname Parliament member lastname.
 * @param firstname Parliament member firstname.
 * @param party Tells in which party parliament member belongs to.
 * @param minister Tells if parliament member is minister.
 * @param pictureUrl Url address for parliament member
 */
@Entity(tableName = "members")
data class ParliamentMember(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "heteka_id")
        val hetekaId: Int,

        @ColumnInfo(name = "seat_number")
        val seatNumber: Int = 0,

        @ColumnInfo(name = "lastname")
        val lastname: String,

        @ColumnInfo(name = "firstname")
        val firstname: String,

        @ColumnInfo(name = "partyName")
        val party: String,

        @ColumnInfo(name = "minister")
        val minister: Boolean = false,

        @ColumnInfo(name = "picture_url")
        val pictureUrl: String = ""
)