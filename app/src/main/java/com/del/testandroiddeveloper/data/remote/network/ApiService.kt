package com.del.testandroiddeveloper.data.remote.network

import com.del.testandroiddeveloper.data.remote.response.ListMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie")
    suspend fun getMovies(
        @Query("api_key") apiKey: String,
    ): ListMovieResponse

    @GET("movie")
    suspend fun getComingSoonMovie(
        @Query("api_key") apiKey: String,
        @Query("language")language: String = "en-US",
        @Query("sort_by")sort: String = "popularity.desc",
        @Query("page")page: Int = 1,
        @Query("year")year: Int = 2023,
    ): ListMovieResponse
}