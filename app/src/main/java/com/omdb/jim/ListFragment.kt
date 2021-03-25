package com.omdb.jim

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.Hold
import com.google.android.material.transition.MaterialElevationScale
import com.omdb.jim.databinding.FragmentListBinding
import com.omdb.jim.db.MovieCacheMapper
import com.omdb.jim.ui.adapter.MovieAdapter
import com.omdb.jim.ui.adapter.MovieSuggestionAdapter
import com.omdb.jim.util.setupToolbar
import com.omdb.jim.vm.ListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    @Inject lateinit var movieCacheMapper: MovieCacheMapper
    private val viewModel: ListViewModel by viewModels()
    private lateinit var binding: FragmentListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exitTransition = Hold()
        reenterTransition = Hold()

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    @ExperimentalPagingApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val movieAdapter = MovieAdapter() { movie, view ->

            val extras = FragmentNavigatorExtras(view to "shared_element_container")
            val action = ListFragmentDirections.actionListFragmentToMovieFragment(movie.posterUrl, movie.title)

            findNavController().navigate(action, extras)
        }

        binding = FragmentListBinding.inflate(inflater).apply {
            vm = viewModel
            lifecycleOwner = this@ListFragment

            toolbar.apply {
                setupToolbar(this)
//                inflateMenu(R.menu.menu_list)
            }

            // Initialize searchView with suggestions
            searchView.setOnQueryTextListener(this@ListFragment)

            // Initialize swipeRefreshView
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        Timber.d("onQueryTextSubmit: $query")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrEmpty()) return true
        Timber.d("onQueryTextChange: $newText")

        // Get cursor from MovieDao
        lifecycleScope.launch {
            val cursor = viewModel.getMoviesCursor(newText ?: "")
            binding.searchView.suggestionsAdapter = MovieSuggestionAdapter(requireContext(), cursor)
        }
        return true
    }

}