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
            plot = cache.plot,
            imdbRating = cache.imdbRating,
            imdbVotes = cache.imdbVotes,
            rated = cache.rated,
            runtime = cache.runtime,
            genre = cache.genre,
            actors = cache.actors,
        )
    }

    fun mapToEntity(model: Movie): MovieCache {
        return MovieCache(
            imdbId = model.imdbId,
            title = model.title,
            year = model.year,
            posterUrl = model.posterUrl,
            type = model.type,
            plot = model.plot,
            imdbRating = model.imdbRating,
            imdbVotes = model.imdbVotes,
            rated = model.rated,
            runtime = model.runtime,
            genre = model.genre,
            actors = model.actors,
        )
    }

    fun mapToEntityList(models: List<Movie>): List<MovieCache> = models.map {
        mapToEntity(it)
    }

    fun mapFromEntityList(caches: List<MovieCache>): List<Movie> = caches.map {
        mapFromEntity(it)
    }
}