package com.del.testandroiddeveloper.domain.repository

import com.del.testandroiddeveloper.data.Resource
import com.del.testandroiddeveloper.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getAllMovies(): Flow<Resource<List<Movie>>>

    fun getComingSoonMovie(): Flow<Resource<List<Movie>>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    fun getSearchMovies(search: String): Flow<List<Movie>>

    fun getSearchFavoriteMovies(search: String): Flow<List<Movie>>

    suspend fun setMovieFavorite(movie: Movie, state: Boolean)

}