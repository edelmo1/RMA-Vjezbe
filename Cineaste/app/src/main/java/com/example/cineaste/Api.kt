package com.example.cineaste

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<GetMoviesResponse>

    @GET("search/movie")
    suspend fun getSearchMovies(
//PAZI NA REDOSLIJED PARAMETARA POSTO API_KEY JE DEFAULTNI
        @Query("query") text : String,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<GetMoviesResponse>

}

object ApiAdapter {
    val retrofit : Api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/") //u objektu ide pocetak a nastavak u @GET
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)
}