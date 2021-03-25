package com.omdb.jim.model

data class Movie(
    val imdbId: String,
    val title: String,
    val year: String,
    val posterUrl: String,
    val type: String,
    val plot: String = "",
    val imdbRating: String = "",
    val imdbVotes: String = "",
    val rated: String = "",
    val runtime: String = "",
    val genre: String = "",
    val actors: String = "",
)