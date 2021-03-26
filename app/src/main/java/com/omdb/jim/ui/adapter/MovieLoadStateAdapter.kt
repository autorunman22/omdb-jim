package com.omdb.jim.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.omdb.jim.R
import com.omdb.jim.databinding.LayoutLoadStateItemBinding
import timber.log.Timber

class LoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.layout_load_state_item, parent, false)
) {
    private val binding = LayoutLoadStateItemBinding.bind(itemView)
    private val progressBar = binding.progressBar
    private val btnRetry = binding.btnRetry.also {
        it.setOnClickListener {
            retry()
        }
    }

    fun bind(loadState: LoadState) {
        Timber.d("binding")
        progressBar.isVisible = true
        btnRetry.isVisible = true
    }
}

class MovieLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) : LoadStateViewHolder {
        Timber.d("Creating viewholder")
        return LoadStateViewHolder(parent, retry)
    }
}