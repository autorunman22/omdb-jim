package com.omdb.jim.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
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

fun Fragment.hideKeyboard() {
    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view!!.windowToken, 0)
}