package com.omdb.jim

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.omdb.jim.databinding.FragmentMovieBinding
import com.omdb.jim.util.setupToolbar
import timber.log.Timber


class MovieFragment : Fragment() {

    private val args: MovieFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        return binding.root
    }

}