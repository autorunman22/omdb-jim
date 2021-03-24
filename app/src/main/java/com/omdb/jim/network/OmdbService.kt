package com.omdb.jim.network

import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbService {

    @GET("/")
    suspend fun search(
        @Query("apikey") apiKey: String,
        @Query("y") year: String,
        @Query("s") query: String,
        @Query("page") page: Int
    ): List<MovieNetwork>
}