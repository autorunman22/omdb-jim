package com.omdb.jim.ui.adapter

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cursoradapter.widget.CursorAdapter
import android.widget.TextView
import coil.load
import com.omdb.jim.R

class MovieSuggestionAdapter(context: Context, cursor: Cursor) :
    CursorAdapter(context, cursor, false) {

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.layout_movie_suggestion_item, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val title = cursor?.getString(cursor.getColumnIndexOrThrow("title"))
        val url = cursor?.getString(cursor.getColumnIndexOrThrow("poster_url"))

        val tvTitle = view?.findViewById(R.id.tv_title) as TextView
        tvTitle.text = title

        val ivPoster = view.findViewById(R.id.iv_poster) as ImageView
        ivPoster.load(url)
    }
}