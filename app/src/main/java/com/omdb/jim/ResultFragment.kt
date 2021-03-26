package com.omdb.jim

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.Hold
import com.omdb.jim.databinding.FragmentResultBinding
import com.omdb.jim.model.Movie
import com.omdb.jim.util.setupToolbar
import com.omdb.jim.vm.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint

interface OnResultTap {
    fun onTap(movie: Movie, v: View)
}

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private val args: ResultFragmentArgs by navArgs()
    private val viewModel by viewModels<ResultViewModel>()
    private lateinit var binding: FragmentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setQuery(args.query.trim())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition() }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false).apply {
            setupToolbar(toolbar)
            tvResultFor.text = getString(R.string.results_for, args.query)
            rvResults.apply {
                layoutManager = LinearLayoutManager(context)
            }
        }

        viewModel.results.observe(viewLifecycleOwner) {
            binding.rvResults.apply {
                withModels {
                    it.forEachIndexed { index, m ->
                        layoutMovieResultItem {
                            id(index)
                            movie(m)
                            vm(viewModel)
                            tap(object : OnResultTap {
                                override fun onTap(movie: Movie, v: View) {
                                    exitTransition = Hold()
                                    reenterTransition = Hold()

                                    val extras = FragmentNavigatorExtras(v to "shared_element_container")
                                    val action =
                                        ResultFragmentDirections.actionResultFragmentToMovieFragment(
                                            movie.posterUrl,
                                            movie.title,
                                            movie.imdbId
                                        )

                                    findNavController().navigate(action, extras)
                                }
                            })
                        }
                    }
                }
            }
        }

        return binding.root
    }

}