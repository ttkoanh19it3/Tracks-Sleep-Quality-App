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

package com.example.android.trackmysleepquality.database

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.example.android.trackmysleepquality.database.SleepDatabase as SleepDatabase1

@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(
        @PrimaryKey(autoGenerate = true)
        var nightId: Long = 0L,

        @ColumnInfo(name = "start_time_milli")
        val startTimeMilli: Long = System.currentTimeMillis(),

        @ColumnInfo(name = "end_time_milli")
        var endTimeMilli: Long = startTimeMilli,

        @ColumnInfo(name = "quality_rating")
        var sleepQuality: Int = -1


)
@Database(entities = [SleepNight::class], version = 1, exportSchema = false)
abstract class SleepDatabase : RoomDatabase() {

        abstract val sleepDatabaseDao: SleepDatabaseDao

        companion object {

                @Volatile
                private var INSTANCE: SleepDatabase1? = null

                fun getInstance(context: Context): SleepDatabase1 {
                        synchronized(this) {
                                var instance = INSTANCE

                                if (instance == null) {
                                        instance = Room.databaseBuilder(
                                                context.applicationContext,
                                                SleepDatabase1::class.java,
                                                "sleep_history_database"
                                        )
                                                .fallbackToDestructiveMigration()
                                                .build()
                                        INSTANCE = instance
                                }
                                return instance
                        }
                }
        }
}