package com.omdb.jim.network

import com.google.gson.annotations.SerializedName

class MovieNetwork(
    @SerializedName("imdbId")
    val imdbId: String,
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("Poster")
    val posterUrl: String,
    @SerializedName("Type")
    val type: String,
)