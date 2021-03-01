package com.jhiltunen.finnishparliamentmembers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ParliamentMember::class], version = 1, exportSchema = false)
abstract class ParliamentDatabase : RoomDatabase() {
    abstract fun parliamentDatabaseDao() : ParliamentDatabaseDao
    companion object {
        @Volatile
        private var INSTANCE: ParliamentDatabase? = null

        fun getInstance(context: Context): ParliamentDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ParliamentDatabase::class.java,
                        "parliament_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}