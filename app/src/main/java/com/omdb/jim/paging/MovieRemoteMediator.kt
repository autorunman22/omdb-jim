package com.omdb.jim.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.omdb.jim.BuildConfig
import com.omdb.jim.db.AppDatabase
import com.omdb.jim.db.MovieCache
import com.omdb.jim.db.MovieCacheMapper
import com.omdb.jim.network.MovieNetMapper
import com.omdb.jim.network.OmdbService
import timber.log.Timber
import javax.inject.Inject

@ExperimentalPagingApi
class MovieRemoteMediator @Inject constructor(
    private val appDatabase: AppDatabase,
    private val omdbService: OmdbService,
    private val movieNetMapper: MovieNetMapper,
    private val movieCacheMapper: MovieCacheMapper,
) : RemoteMediator<Int, MovieCache>(){

    private val movieDao = appDatabase.movieDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieCache>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                    lastItem.imdbId
                }
            }
            Timber.d("computed loadKey: $loadKey")
            val response = omdbService.search(BuildConfig.OMDB_API_KEY, "2020", loadKey?.toInt() ?: 33, "for")
            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearAll()
                }

                val movies = movieNetMapper.mapFromEntityList(response.search)
                movieDao.insertAll(movieCacheMapper.mapToEntityList(movies))
            }

            MediatorResult.Success(endOfPaginationReached = false)
        } catch (e: Exception) {
            Timber.d("Error fetching movie search: ${e.message}")
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }
}