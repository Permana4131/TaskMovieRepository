package com.del.testandroiddeveloper.data.local

import com.del.testandroiddeveloper.data.local.entity.MovieEntity
import com.del.testandroiddeveloper.data.local.room.MovieDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class LocalDataSource constructor(private val movieDao: MovieDao){

    fun getAllMovies(): Flow<List<MovieEntity>> {
        return movieDao.getMovies()
    }

    fun getComingSoonMovie(): Flow<List<MovieEntity>> {
        return movieDao.getComingSoonMovies()
    }

    fun getAllFavoriteMovies(): Flow<List<MovieEntity>> {
        return movieDao.getFavoriteMovies()
    }

    fun getMovieSearch(search: String): Flow<List<MovieEntity>> {
        return movieDao.getSearchMovies(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    fun getMovieFavoriteSearch(search: String):Flow<List<MovieEntity>>{
        return movieDao.getSearchFavoriteMovies(search)
            .flowOn(Dispatchers.Default)
            .conflate()
    }

    suspend fun insertMovies(movies: List<MovieEntity>) = movieDao.insertMovie(movies)

    suspend fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        movieDao.updateFavoriteMovie(movie)
    }
}