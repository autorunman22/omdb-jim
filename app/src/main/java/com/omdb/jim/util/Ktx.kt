package com.omdb.jim.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.omdb.jim.R

@BindingAdapter("srcByUrl")
fun imageViewBySrc(imageView: ImageView, url: String) {
    if (url.isNullOrEmpty()) {
        imageView.load(R.drawable.sample_movie_poster)
        return
    }
    if (url == "N/A") imageView.load(R.drawable.sample_movie_poster)
    else imageView.load(url) {
        crossfade(true)
    }
}

fun View.snack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}