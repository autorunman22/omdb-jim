package com.omdb.jim.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class MovieCache(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "imdbId")
    val imdbId: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "year")
    val year: String,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String,
    @ColumnInfo(name = "type")
    val type: String,
)