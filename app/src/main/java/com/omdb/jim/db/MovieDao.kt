package com.omdb.jim.db

import android.database.Cursor
import androidx.paging.PagingSource
import androidx.room.*

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieCaches: List<MovieCache>)

    @Update
    suspend fun update(movieCache: MovieCache)

    @Query("SELECT * from movies")
    fun movies(): List<MovieCache>

    @Query("SELECT * from movies WHERE imdbId = :imdbId")
    fun movieByImdbId(imdbId: String): MovieCache?

    @Query("DELETE from movies")
    suspend fun clearAll()

    @Query("SELECT * from movies")
    fun pagingSource(): PagingSource<Int, MovieCache>

    @Query("SELECT m.*, m.imdbId as _id FROM movies m WHERE title LIKE :query")
    fun getMoviesCursor(query: String): Cursor

    @Query("SELECT * from movies WHERE title LIKE :q AND type LIKE :type")
    fun getMoviesByQuery(q: String, type: String): List<MovieCache>
}