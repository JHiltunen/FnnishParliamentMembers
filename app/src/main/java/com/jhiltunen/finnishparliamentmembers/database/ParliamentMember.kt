package com.jhiltunen.finnishparliamentmembers.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parliament_members")
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

        @ColumnInfo(name = "party")
        val party: String,

        @ColumnInfo(name = "minister")
        val minister: Boolean = false,

        @ColumnInfo(name = "picture_url")
        val pictureUrl: String = ""
)