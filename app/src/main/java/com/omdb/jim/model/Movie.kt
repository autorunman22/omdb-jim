package com.omdb.jim.model

data class Movie(
    val imdbId: String,
    val title: String,
    val year: String,
    val posterUrl: String,
    val type: String,
)