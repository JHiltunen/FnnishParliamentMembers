package com.jhiltunen.finnishparliamentmembers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ParliamentMember::class], version = 1, exportSchema = false)
abstract class ParliamentMembersDatabase : RoomDatabase() {

    abstract val parliamentMembersDatabaseDao: ParliamentMembersDao

    companion object {
        @Volatile
        private var INSTANCE: ParliamentMembersDatabase? = null

        fun getInstance(context: Context): ParliamentMembersDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ParliamentMembersDatabase::class.java,
                        "parliament_members_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}