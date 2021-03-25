package com.omdb.jim

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.omdb.jim.databinding.FragmentResultBinding
import com.omdb.jim.network.DataState
import com.omdb.jim.util.setupToolbar
import com.omdb.jim.vm.ResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private val args: ResultFragmentArgs by navArgs()
    private val viewModel by viewModels<ResultViewModel>()
    private lateinit var binding: FragmentResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setQuery(args.query)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false).apply {
            setupToolbar(toolbar)
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
                        }
                    }
                }
            }
        }

        return binding.root
    }

}