package com.del.testandroiddeveloper.domain.usecase

import com.del.testandroiddeveloper.data.Resource
import com.del.testandroiddeveloper.domain.model.Movie
import com.del.testandroiddeveloper.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieAppInteractor(private val iMovieAppRepository: IMovieRepository) : MovieAppUseCase {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        iMovieAppRepository.getAllMovies()

    override fun getComingSoonMovie(): Flow<Resource<List<Movie>>> =
        iMovieAppRepository.getComingSoonMovie()

    override fun getFavoriteMovies(): Flow<List<Movie>> =
        iMovieAppRepository.getFavoriteMovies()

    override fun getSearchMovies(search: String): Flow<List<Movie>> =
        iMovieAppRepository.getSearchMovies(search)

    override fun getSearchFavoriteMovies(search: String): Flow<List<Movie>> = iMovieAppRepository.getSearchFavoriteMovies(search)


    override suspend fun setMovieFavorite(movie: Movie, state: Boolean) =
        iMovieAppRepository.setMovieFavorite(movie, state)

}