package com.example.cineaste

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieDetailResultActivity : AppCompatActivity() {

    private  var movie= Movie(0, "Test", "Test", "Test", "Test","Test","test")
    private lateinit var title : TextView
    private lateinit var overview : TextView
    private lateinit var releaseDate : TextView
    private lateinit var website : TextView
    private lateinit var poster : ImageView
    private lateinit var backdrop : ImageView
    private lateinit var button : Button
    private lateinit var addFavorite : Button
    private val posterPath = "https://image.tmdb.org/t/p/w780"
    private val backdropPath = "https://image.tmdb.org/t/p/w500"

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail_result)

        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        releaseDate = findViewById(R.id.movie_release_date)
        poster = findViewById(R.id.movie_poster)
        website = findViewById(R.id.movie_website)
        backdrop = findViewById(R.id.movie_backdrop)
        button =  findViewById(R.id.button2)
        addFavorite = findViewById(R.id.FavoriteButton)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(123)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if(intent?.getParcelableExtra("movie", Movie::class.java)!==null) {
                movie = intent?.getParcelableExtra("movie", Movie::class.java)!!
                populateDetails()
            }
        } else {
            if (intent?.getParcelableExtra<Movie>("movie") !== null) {
                movie = intent?.getParcelableExtra<Movie>("movie")!!
                populateDetails()
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

        lifecycleScope.launch {
            var filmovi = getDB(applicationContext)
            var postoji = false
            for (film in filmovi) {
                if (film.title == movie.title) {
                    postoji = true
                    addFavorite.text = "Ukloni omiljeni"
                }
            }

            if(postoji==false) {
                addFavorite.setOnClickListener {
                    writeDB(applicationContext, movie)
                    addFavorite.text = "Ukloni omiljeni"

                    addFavorite.setOnClickListener {
                        deleteDB(applicationContext, movie)
                        addFavorite.text = "Dodaj omiljeni"
                    }
                }
            }

            if(postoji==true) {
                addFavorite.setOnClickListener {
                    deleteDB(applicationContext, movie)
                    addFavorite.text = "Dodaj omiljeni"

                    addFavorite.setOnClickListener {
                        writeDB(applicationContext, movie)
                        addFavorite.text = "Ukloni omiljeni"
                    }
                }
            }

        }




    }

    private fun populateDetails() {
        title.text=movie.title
        releaseDate.text=movie.releaseDate
        website.text=movie.homepage
        overview.text=movie.overview
        val context: Context = poster.getContext()
        var id = 0;
       // if (movie.genre!==null) id = context.getResources().getIdentifier(movie.genre, "drawable", context.getPackageName())
        id=0
        if (id===0) id=context.getResources()
            .getIdentifier("picture1", "drawable", context.getPackageName())
        Glide.with(context)
            .load(posterPath + movie.posterPath)
            .placeholder(R.drawable.picture1)
            .error(id)
            .fallback(id)
            .into(poster);
        var backdropContext: Context = backdrop.getContext()
        Glide.with(backdropContext)
            .load(backdropPath + movie.backdropPath)
            .centerCrop()
            .placeholder(R.drawable.backdrop)
            .error(R.drawable.backdrop)
            .fallback(R.drawable.backdrop)
            .into(backdrop);
    }

     suspend fun getDB(context:Context): List<Movie> {
        return withContext(Dispatchers.IO){return@withContext MovieRepository.getFavoriteMovies(context)} }

    fun writeDB(context: Context, movie:Movie){
        lifecycleScope.launch{
            val result = MovieRepository.writeFavorite(context,movie)
            when (result) {
                is String -> onSuccess1(result)
                else-> onError()
            }
        }
    }

    fun deleteDB(context: Context, movie:Movie){
        lifecycleScope.launch{
            val result = MovieRepository.deleteFavorite(context,movie)
            when (result) {
                is String -> onSuccess1(result)
                else-> onError()
            }
        }
    }
    fun onSuccess1(message:String){
        val toast = Toast.makeText(applicationContext, "Spaseno", Toast.LENGTH_SHORT)
        toast.show()
        //addFavorite.visibility= View.GONE
    }
    fun onError() {
        val toast = Toast.makeText(applicationContext, "Greska", Toast.LENGTH_SHORT)
        toast.show()
    }


}