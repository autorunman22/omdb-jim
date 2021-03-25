package com.omdb.jim.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.omdb.jim.R

@BindingAdapter("srcByUrl")
fun imageViewBySrc(imageView: ImageView, url: String) {
    if (url == "N/A") imageView.load(R.drawable.sample_movie_poster)
    else imageView.load(url) {
        crossfade(true)
    }
}