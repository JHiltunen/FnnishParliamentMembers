package com.jhiltunen.finnishparliamentmembers.database

import androidx.room.*

@Entity(tableName = "members_likes", indices = [Index(value = ["heteka_id"], unique = true)], foreignKeys = [ForeignKey(entity = ParliamentMember::class, parentColumns = arrayOf("heteka_id"), childColumns = arrayOf("heteka_id"), onDelete = ForeignKey.CASCADE)])
data class MemberLikes(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "heteka_id")
        val hetekaId: Int,
        val likes: Int = 0
)