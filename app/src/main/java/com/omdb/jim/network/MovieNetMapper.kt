package com.omdb.jim.network

import com.omdb.jim.model.Movie
import javax.inject.Inject

class MovieNetMapper @Inject constructor() {

    fun mapFromEntity(cache: MovieNetwork): Movie {
        return Movie(
            imdbId = cache.imdbId,
            title = cache.title,
            year = cache.year,
            posterUrl = cache.posterUrl,
            type = cache.type,
        )
    }

    fun mapToEntity(model: Movie): MovieNetwork {
        return MovieNetwork(
            imdbId = model.imdbId,
            title = model.title,
            year = model.year,
            posterUrl = model.posterUrl,
            type = model.type,
        )
    }
}