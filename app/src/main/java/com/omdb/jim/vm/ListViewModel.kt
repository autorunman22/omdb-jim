package com.omdb.jim.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.omdb.jim.db.MovieCache
import com.omdb.jim.db.MovieDao
import com.omdb.jim.paging.MovieRemoteMediator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ListViewModel @ExperimentalPagingApi
@Inject constructor(
    movieDao: MovieDao,
    remoteMediator: MovieRemoteMediator
): ViewModel() {

    @ExperimentalPagingApi
    val movieListFlow: StateFlow<PagingData<MovieCache>> = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = remoteMediator,
    ) {
        movieDao.pagingSource()
    }.flow.cachedIn(viewModelScope).stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())
}