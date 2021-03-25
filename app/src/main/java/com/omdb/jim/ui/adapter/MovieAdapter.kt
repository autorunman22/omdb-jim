package com.omdb.jim.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.omdb.jim.databinding.LayoutMovieItemBinding as Binding
import com.omdb.jim.model.Movie

class MovieAdapter(private val onMovieClick: (Movie) -> Unit) :
    PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val m = getItem(position) ?: return
        holder.binding.apply {
            movie = m
            mcvParent.setOnClickListener {
                onMovieClick.invoke(m)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbId == newItem.imdbId
            }

            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class MovieViewHolder(val binding: Binding) :
        RecyclerView.ViewHolder(binding.root)
}