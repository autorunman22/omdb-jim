package com.omdb.jim.vm

import androidx.lifecycle.*
import com.omdb.jim.db.MovieCacheMapper
import com.omdb.jim.model.Movie
import com.omdb.jim.network.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val movieCacheMapper: MovieCacheMapper
) : ViewModel() {

    private val mQuery = MutableLiveData<String>()
    private val mType = MutableLiveData<String>()

    fun setQuery(q: String) {
        mQuery.value = q
    }

    fun setType(type: String) {
        mType.value = type
    }

    private val mResults = MediatorLiveData<List<Movie>>()
    val results: LiveData<List<Movie>> = mResults

    init {
        mResults.addSource(mQuery) {
           viewModelScope.launch(Dispatchers.IO) {
               val movies = movieCacheMapper.mapFromEntityList(movieRepository.getMoviesByQuery(it, mType.value ?: ""))
               mResults.postValue(movies)
           }
        }

        mResults.addSource(mType) {
            viewModelScope.launch(Dispatchers.IO) {
                val movies = movieCacheMapper.mapFromEntityList(movieRepository.getMoviesByQuery(mQuery.value!!, it))
                mResults.postValue(movies)
            }
        }

    }

}