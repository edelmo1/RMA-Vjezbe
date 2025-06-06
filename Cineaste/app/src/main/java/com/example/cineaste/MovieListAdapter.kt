package com.example.cineaste

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.bumptech.glide.Glide


class MovieListAdapter(
    private var movies: List<Movie>,
    private val posterPath: String = "https://image.tmdb.org/t/p/w342",
    private val onItemClicked: (movie:Movie) -> Unit

) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder
    {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }
    override fun getItemCount(): Int = movies.size
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieTitle.text = movies[position].title;

        val context: Context = holder.movieImage.getContext()
        var id: Int = 0;
        //if (genreMatch!==null) id = context.getResources().getIdentifier(genreMatch, "drawable", context.getPackageName())
        if (id===0) id=context.getResources()
            .getIdentifier("picture1", "drawable", context.getPackageName())
        Glide.with(context)
            .load(posterPath + movies[position].posterPath)
            .centerCrop()
            .placeholder(R.drawable.picture1)
            .error(id)
            .fallback(id)
            .into(holder.movieImage);


        holder.itemView.setOnClickListener{
            onItemClicked(movies[position])
        }


    }
    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }
    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieImage: ImageView = itemView.findViewById(R.id.movieImage)
        val movieTitle: TextView = itemView.findViewById(R.id.movieTitle)
    }
}