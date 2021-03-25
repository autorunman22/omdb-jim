package com.omdb.jim

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.omdb.jim.databinding.FragmentMovieBinding
import com.omdb.jim.network.DataState
import com.omdb.jim.util.setupToolbar
import com.omdb.jim.util.snack
import com.omdb.jim.vm.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val args: MovieFragmentArgs by navArgs()
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initial fetching from the network
        viewModel.fetchByImdbId(args.imdbId)

        sharedElementEnterTransition = MaterialContainerTransform()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMovieBinding.inflate(inflater).apply {
            posterUrl = args.posterUrl
            title = args.title

            setupToolbar(toolbar)
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> binding.loader.isVisible = true
                is DataState.Error -> {
                    binding.loader.isVisible = false
                    binding.root.snack("Error! ${it.exception.localizedMessage}")
                }
                is DataState.Success -> {
                    binding.loader.isVisible = false
                    binding.movie = it.data
                }
            }
        }

        return binding.root
    }

}