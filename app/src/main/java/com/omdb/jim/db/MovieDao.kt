package com.omdb.jim.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieCaches: List<MovieCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieCache: MovieCache)

    @Query("SELECT * from movies")
    fun movies(): List<MovieCache>

    @Query("DELETE from movies")
    suspend fun clearAll()

    @Query("SELECT * from movies")
    fun pagingSource(): PagingSource<Int, MovieCache>
}