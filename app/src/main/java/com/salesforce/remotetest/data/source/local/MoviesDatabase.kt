package com.salesforce.remotetest.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salesforce.remotetest.data.Movie

/*
 * The Room Database that contains the Movie table.
 */
@Database(entities = arrayOf(Movie::class), version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        private var INSTANCE: MoviesDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): MoviesDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MoviesDatabase::class.java, "Movies.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}