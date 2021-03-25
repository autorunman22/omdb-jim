package com.omdb.jim.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omdb.jim.model.Movie
import com.omdb.jim.network.DataState
import com.omdb.jim.network.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    ViewModel() {

    private val mMovie = MutableLiveData<DataState<Movie>>()
    val movie: LiveData<DataState<Movie>> = mMovie

    fun fetchByImdbId(imdbId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.fetchMovieByImdbId(imdbId).collect {
                mMovie.postValue(it)
            }
        }
    }
}