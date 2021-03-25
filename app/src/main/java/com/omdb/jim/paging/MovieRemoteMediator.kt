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
import com.omdb.jim.db.MovieRemoteKeys
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
    private val remoteKeysDao = appDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieCache>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    val key = getRemoteKeysClosestToCurrentPosition(state)
                    Timber.d("REFRESH key: $key, output: ${key?.nextKey?.minus(1)}")
                    key?.nextKey?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    Timber.d("PREPEND key requested")
                    return MediatorResult.Success(true)
                }
                LoadType.APPEND -> {
                    val key = getRemoteKeysForLastItem(state)
                    Timber.d("APPEND key: $key")
                    key?.nextKey ?: return MediatorResult.Success(true)
                }
            }

            Timber.d("computed loadKey: $loadKey")

            val response = omdbService.search(BuildConfig.OMDB_API_KEY, "2020", loadKey, "for")
            val endOfPaginationReached = response.search.size < state.config.pageSize

            appDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    movieDao.clearAll()
                }

                val prevKey = if (loadKey == 1) null else loadKey - 1
                val nextKey = if (endOfPaginationReached) null else loadKey + 1
                val remoteKeys = response.search.map {
                    MovieRemoteKeys(imdbId = it.imdbId, prevKey = prevKey, nextKey = nextKey)
                }

                remoteKeysDao.insertAll(remoteKeys)

                val movies = movieNetMapper.mapFromEntityList(response.search)
                movieDao.insertAll(movieCacheMapper.mapToEntityList(movies))
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Timber.d("Error fetching movie search: ${e.message}")
            e.printStackTrace()
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, MovieCache>): MovieRemoteKeys? {
        return state.lastItemOrNull()?.let { movie ->
            appDatabase.withTransaction {
                appDatabase.remoteKeysDao().remoteKeysByImdbId(movie.imdbId)
            }
        }
    }

    private suspend fun getRemoteKeysClosestToCurrentPosition(state: PagingState<Int, MovieCache>): MovieRemoteKeys? {
        val anchorPosition = state.anchorPosition
        Timber.d("anchorPosition: $anchorPosition")
        return anchorPosition?.let {
            state.closestItemToPosition(it)?.imdbId?.let { imdbId ->
                appDatabase.withTransaction {
                    appDatabase.remoteKeysDao().remoteKeysByImdbId(imdbId)
                }
            }
        }
    }
}