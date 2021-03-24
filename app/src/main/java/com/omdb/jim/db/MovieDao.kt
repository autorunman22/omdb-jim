package com.omdb.jim.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieCache: MovieCache)

    @Query("SELECT * from movies")
    fun movies(): List<MovieCache>
}