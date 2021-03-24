package com.omdb.jim.db

import com.omdb.jim.model.Movie
import javax.inject.Inject

class MovieCacheMapper @Inject constructor() {

    fun mapFromEntity(cache: MovieCache): Movie {
        return Movie(
            imdbId = cache.imdbID,
            title = cache.title,
            year = cache.year
        )
    }

    fun mapToEntity(model: Movie): MovieCache {
        return MovieCache(
            imdbID = model.imdbId,
            title = model.title,
            year = model.year,
        )
    }
}