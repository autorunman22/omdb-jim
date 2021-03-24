package com.omdb.jim.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieCache::class], version = 2)
abstract class AppDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DB_NAME = "movies"
    }
}