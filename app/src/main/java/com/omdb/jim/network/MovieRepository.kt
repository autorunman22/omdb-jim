package com.omdb.jim.network

import com.omdb.jim.db.MovieCacheMapper
import com.omdb.jim.db.MovieDao
import com.omdb.jim.model.Movie
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject


class MovieRepository @Inject constructor(private val movieDao: MovieDao,
                                          private val cacheMapper: MovieCacheMapper,
                                          private val omdbService: OmdbService) {

    suspend fun fetchMovieByImdbId(imdbId: String) = flow {
        emit(DataState.Loading)
        kotlinx.coroutines.delay(2_000)
        try {
            val movieNetwork = omdbService.getMovie(imdbId)

            val movie = Movie(
                imdbId = movieNetwork.imdbId,
                title = movieNetwork.title,
                year = movieNetwork.year,
                posterUrl = movieNetwork.poster,
                type = movieNetwork.type,
                plot = movieNetwork.plot,
                imdbRating = movieNetwork.imdbRating,
                imdbVotes = movieNetwork.imdbVotes,
                rated = movieNetwork.rated,
                genre = movieNetwork.genre,
                actors = movieNetwork.actors,
            )

            movieDao.update(cacheMapper.mapToEntity(movie))

            Timber.d("Movie details response: $movieNetwork")
            emit(DataState.Success(movie))
        } catch (e: Exception) {
            Timber.d("Error fetching movie: ${e.message}")
            emit(DataState.Error(e))
        }

    }
}