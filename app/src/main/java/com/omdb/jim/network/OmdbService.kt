package com.omdb.jim.network

import com.omdb.jim.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface OmdbService {

    @GET("/")
    suspend fun search(
        @Query("apikey") apiKey: String,
        @Query("y") year: String,
        @Query("page") page: Int = 1,
        @Query("s") query: String,
    ): OmdbResponse

    @GET("/")
    suspend fun getMovie(@Query("i") imdbId: String,
                         @Query("apikey") apiKey: String = BuildConfig.OMDB_API_KEY): MovieDetailResponse
}