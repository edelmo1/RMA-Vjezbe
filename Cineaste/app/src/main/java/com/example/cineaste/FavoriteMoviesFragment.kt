package com.example.cineaste
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

class FavoriteMoviesFragment : Fragment() {
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter
    private var favoriteMoviesList =  getFavoriteMovies()
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.favorites_fragment, container, false)
       favoriteMovies = view.findViewById(R.id.favMovies)
       favoriteMovies.layoutManager = GridLayoutManager(activity, 2)
        favoriteMoviesAdapter = MovieListAdapter(arrayListOf()) { movie -> showMovieDetails(movie) }
        favoriteMovies.adapter=favoriteMoviesAdapter
       favoriteMoviesAdapter.updateMovies(favoriteMoviesList)

        context?.let {
            getFavorites(it)
        }
        return view;
    }
    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(activity, MovieDetailResultActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }

    fun getFavorites(context: Context){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        // Create a new coroutine on the UI thread
        scope.launch{

            // Make the network call and suspend execution until it finishes
            val result = MovieRepository.getFavoriteMovies(context)

            // Display result of the network request to the user
            when (result) {
                is List<Movie> -> onSuccess(result)
                else-> onError()
            }
        }
    }
    fun onSuccess(movies:List<Movie>){
        favoriteMoviesAdapter.updateMovies(movies)
    }
    fun onError() {
        val toast = Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
        toast.show()
    }
}