package com.omdb.jim.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieRemoteKeys(
    @PrimaryKey
    val imdbId: String,
    val prevKey: Int?,
    val nextKey: Int?,
)