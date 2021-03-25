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
    @ColumnInfo(name = "plot")
    val plot: String,
    @ColumnInfo(name = "imdb_rating")
    val imdbRating: String,
    @ColumnInfo(name = "imdb_votes")
    val imdbVotes: String,
    @ColumnInfo(name = "rated")
    val rated: String,
    @ColumnInfo(name = "runtime")
    val runtime: String,
    @ColumnInfo(name = "genre")
    val genre: String,
    @ColumnInfo(name = "actors")
    val actors: String,
)