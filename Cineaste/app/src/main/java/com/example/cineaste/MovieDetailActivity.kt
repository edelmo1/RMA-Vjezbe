package com.example.cineaste

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var movie: Movie
    private lateinit var title : TextView
    private lateinit var overview : TextView
    private lateinit var releaseDate : TextView
    private lateinit var website : TextView
    private lateinit var poster : ImageView
    private lateinit var button : Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)




        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        releaseDate = findViewById(R.id.movie_release_date)
        poster = findViewById(R.id.movie_poster)
        website = findViewById(R.id.movie_website)
        button = findViewById(R.id.button2)

        val extras = intent.extras
        if (extras != null) {
            movie = getMovieByTitle(extras.getString("movie_title",""))
            populateDetails()
        } else {
            finish()
        }

        website.setOnClickListener{
            showWebsite()
        }
        title.setOnClickListener{
            val movieTitle = Uri.encode(movie.title)
            val query = "$movieTitle trailer"
            val searchUri = Uri.parse("https://www.google.com/search?q=$query")
            val webIntent = Intent(Intent.ACTION_VIEW, searchUri)
            try {
                startActivity(webIntent)
            } catch (e: ActivityNotFoundException) {
// Definisati naredbe ako ne postoji aplikacija za navedenu akciju
            }
        }

        button.setOnClickListener {
            // Kreiranje tekstualne poruke
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,movie.overview )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }


    }

    private fun showWebsite(){
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(movie.homepage))
        try {
            startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
// Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }
    }

    private fun populateDetails() {
        title.text=movie.title
        releaseDate.text=movie.releaseDate
        website.text=movie.homepage
        overview.text=movie.overview
        val context: Context = poster.context
       // var id: Int = context.resources.getIdentifier(movie.genre, "drawable", context.packageName)
       var id: Int = 0
        if (id==0) id=context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        poster.setImageResource(id)
    }

   private fun getMovieByTitle(name:String):Movie {
        val movies: ArrayList<Movie> = arrayListOf()
        movies.addAll(getRecentMovies())
        movies.addAll(getFavoriteMovies())
        val movie = movies.find { movie -> name == movie.title }
        return movie ?: Movie(0, "Test", "Test", "Test", "Test" ,"Test","test")
    }

}