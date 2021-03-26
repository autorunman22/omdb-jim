package com.omdb.jim.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKeys: List<MovieRemoteKeys>)

    @Query("SELECT * from movieremotekeys WHERE imdbId = :imdbId")
    fun remoteKeysByImdbId(imdbId: String): MovieRemoteKeys?

    @Query("DELETE from movieremotekeys")
    fun clearRemoteKeys()

    @Query("SELECT * FROM movieremotekeys")
    fun allKeys(): List<MovieRemoteKeys>
}