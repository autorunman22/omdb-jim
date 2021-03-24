package com.omdb.jim.network

import com.google.gson.annotations.SerializedName

data class OmdbResponse(
    @SerializedName("Search")
    val search: List<MovieNetwork>,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("Response")
    val response: String,
)