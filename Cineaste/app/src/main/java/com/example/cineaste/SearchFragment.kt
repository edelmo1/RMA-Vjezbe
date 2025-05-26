package com.example.cineaste

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private lateinit var button : ImageButton
    private lateinit var searchText: EditText
    private lateinit var result : RecyclerView
    private lateinit var resultAdapter: MovieListAdapter
    private var resultList =  listOf<Movie>()

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.search_fragment, container, false)

        button = view.findViewById(R.id.AppCompatImageButton)
        searchText = view.findViewById(R.id.searchText)
        result = view.findViewById(R.id.results)

        result.layoutManager = GridLayoutManager(activity, 2)
        resultAdapter = MovieListAdapter(arrayListOf()) { movie -> showMovieDetails(movie) }
        result.adapter=resultAdapter
        resultAdapter.updateMovies(resultList)
        arguments?.getString("search")?.let {
            searchText.setText(it)
        }

button.setOnClickListener {
    viewLifecycleOwner.lifecycleScope.launch {
        getSearch(searchText.text.toString())
    }
}


        return view;
    }

    //On Click handler
    private fun onClick() {
        val toast = Toast.makeText(context, "Search start", Toast.LENGTH_SHORT)
        toast.show()
        search(searchText.text.toString())
    }
    fun search(query: String){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        // Kreira se Coroutine na UI
        scope.launch{
            // Vrti se poziv servisa i suspendira se rutina dok se `withContext` ne zavrsi
            val result = MovieRepository.searchRequest(query)
            // Prikaze se rezultat korisniku na glavnoj niti
            when (result) {
                is Result.Success<List<Movie>> -> searchDone(result.data)
                else-> onError()
            }
        }
    }

    fun searchDone(movies:List<Movie>){
        val toast = Toast.makeText(context, "Search done", Toast.LENGTH_SHORT)
        toast.show()
        resultAdapter.updateMovies(movies)
        resultAdapter.notifyDataSetChanged()
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }


    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(activity, MovieDetailResultActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intent)
    }

    suspend fun getSearch( text : String){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        // Create a new coroutine on the UI thread
        // Opcija 1
        val result = MovieRepository.getSearchMovies(text)
        // Display result of the network request to the user
        when (result) {
            is GetMoviesResponse -> searchDone(result.movies)
            else-> onError()
        }
    }

}