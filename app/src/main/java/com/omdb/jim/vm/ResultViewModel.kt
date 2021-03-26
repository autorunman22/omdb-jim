package com.omdb.jim.vm

import androidx.lifecycle.*
import com.omdb.jim.db.MovieCacheMapper
import com.omdb.jim.network.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieCacheMapper: MovieCacheMapper
) : ViewModel() {

    private val mQuery = MutableLiveData<String>()
    val query: LiveData<String> = mQuery

    fun setQuery(q: String) {
        mQuery.value = q
    }

    val results = query.switchMap {
        liveData(Dispatchers.IO) {
            emit(movieCacheMapper.mapFromEntityList(movieRepository.getMoviesByQuery(it)))
        }
    }

}