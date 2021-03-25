package com.omdb.jim

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.omdb.jim.databinding.FragmentListBinding
import com.omdb.jim.db.MovieCacheMapper
import com.omdb.jim.ui.adapter.MovieAdapter
import com.omdb.jim.vm.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    @Inject lateinit var movieCacheMapper: MovieCacheMapper
    private val viewModel: ListViewModel by viewModels()

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val movieAdapter = MovieAdapter() {

        }

        val binding = FragmentListBinding.inflate(inflater).apply {
            vm = viewModel
            lifecycleOwner = this@ListFragment

            swipeRefresh.isRefreshing = true
            swipeRefresh.setOnRefreshListener {
                movieAdapter.refresh()
            }

            rvMovies.apply {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.movieListFlow.collect {

                binding.swipeRefresh.isRefreshing = false

                val movies = it.map { cache -> movieCacheMapper.mapFromEntity(cache) }
                movieAdapter.submitData(movies)
            }
        }

        return binding.root
    }
}