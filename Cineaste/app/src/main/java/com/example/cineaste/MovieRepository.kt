package com.example.cineaste

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

    object MovieRepository {


        private const val tmdb_api_key : String = "27124d22d040b2834b316d35d5a23c03"


        suspend fun searchRequest(
            query: String
        ): Result<List<Movie>>{
            return withContext(Dispatchers.IO) {
                try {
                    val movies = arrayListOf<Movie>()
                    val url1 =
                        "https://api.themoviedb.org/3/search/movie?api_key=$tmdb_api_key&query=$query"
                    val url = URL(url1)
                    (url.openConnection() as? HttpURLConnection)?.run {
                        val result = this.inputStream.bufferedReader().use { it.readText() }
                        val jo = JSONObject(result)
                        val results = jo.getJSONArray("results")
                        for (i in 0 until results.length()) {
                            val movie = results.getJSONObject(i)
                            val title = movie.getString("original_title")
                            val id = movie.getInt("id")
                            val posterPath = movie.getString("poster_path")
                            val overview = movie.getString("overview")
                            val releaseDate = movie.getString("release_date")
                            val backdropPath = movie.getString("backdrop_path")
                            movies.add(Movie(id.toLong(), title, overview, releaseDate, null, posterPath,backdropPath))
                            if (i == 6) break  //jer je receno najvise 6 filmova
                        }
                    }
                    return@withContext Result.Success(movies);
                }
                catch (e: MalformedURLException) {
                    return@withContext Result.Error(Exception("Cannot open HttpURLConnection"))
                } catch (e: IOException) {
                    return@withContext  Result.Error(Exception("Cannot read stream"))
                } catch (e: JSONException) {
                    return@withContext Result.Error(Exception("Cannot parse JSON"))
                }

            }
        }


        suspend fun getUpcomingMovies(
        ) : GetMoviesResponse?{
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getUpcomingMovies()
                val responseBody = response.body()
                return@withContext responseBody
            }
        }

        suspend fun getSearchMovies(text : String) : GetMoviesResponse?{
            return withContext(Dispatchers.IO) {
                var response = ApiAdapter.retrofit.getSearchMovies(text)
                val responseBody = response.body()
                return@withContext responseBody
            }
        }


        suspend fun getFavoriteMovies(context: Context) : List<Movie> {
            return withContext(Dispatchers.IO) {
                var db = AppDatabase.getInstance(context)
                var movies = db!!.movieDao().getAll()
                return@withContext movies
            }
        }
        suspend fun writeFavorite(context: Context,movie:Movie) : String?{
            return withContext(Dispatchers.IO) {
                try{
                    var db = AppDatabase.getInstance(context)
                    db!!.movieDao().insertAll(movie)
                    return@withContext "success"
                }
                catch(error:Exception){
                    return@withContext null
                }
            }
        }

        suspend fun deleteFavorite (context: Context,movie:Movie) : String?{
            return withContext(Dispatchers.IO) {
                try{
                    var db = AppDatabase.getInstance(context)
                    db!!.movieDao().deleteAll(movie)
                    return@withContext "success"
                }
                catch(error:Exception){
                    return@withContext null
                }
            }
        }


    }

