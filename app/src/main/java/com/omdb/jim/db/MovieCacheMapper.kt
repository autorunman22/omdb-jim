package com.omdb.jim.db

import com.omdb.jim.model.Movie
import javax.inject.Inject

class MovieCacheMapper @Inject constructor() {

    fun mapFromEntity(cache: MovieCache): Movie {
        return Movie(
            imdbId = cache.imdbId,
            title = cache.title,
            year = cache.year,
            posterUrl = cache.posterUrl,
            type = cache.type,
        )
    }

    fun mapToEntity(model: Movie): MovieCache {
        return MovieCache(
            imdbId = model.imdbId,
            title = model.title,
            year = model.year,
            posterUrl = model.posterUrl,
            type = model.type,
        )
    }

    fun mapToEntityList(models: List<Movie>): List<MovieCache> = models.map {
        mapToEntity(it)
    }
}