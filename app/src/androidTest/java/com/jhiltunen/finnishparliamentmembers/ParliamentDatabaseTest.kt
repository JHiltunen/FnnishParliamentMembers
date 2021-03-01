/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jhiltunen.finnishparliamentmembers

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabase

import com.jhiltunen.finnishparliamentmembers.database.ParliamentDatabaseDao
import com.jhiltunen.finnishparliamentmembers.database.ParliamentMember
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import kotlin.jvm.Throws


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class ParliamentDatabaseTest {
    private lateinit var parliamentDatabaseDao: ParliamentDatabaseDao
    private lateinit var db: ParliamentDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ParliamentDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        parliamentDatabaseDao = db.parliamentDatabaseDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetFirstname() {
        val member = ParliamentMember(
            hetekaId = 1467,
            seatNumber = 64,
            lastname = "Huru",
            firstname = "Petri",
            party = "ps",
            minister = false,
            pictureUrl = "attachment/member/pictures/Huru-Petri-web-v0003-1467.jpg"
        )

        parliamentDatabaseDao.insert(member)
        val memberWithId = parliamentDatabaseDao.getMember(1467)
        assertEquals(memberWithId?.firstname, "Petri")
    }
}