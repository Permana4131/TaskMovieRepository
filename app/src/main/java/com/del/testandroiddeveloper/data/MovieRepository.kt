package com.del.testandroiddeveloper.data

import com.del.testandroiddeveloper.data.local.LocalDataSource
import com.del.testandroiddeveloper.data.remote.RemoteDataSource
import com.del.testandroiddeveloper.data.remote.network.ApiResponse
import com.del.testandroiddeveloper.data.remote.response.MovieResponse
import com.del.testandroiddeveloper.domain.model.Movie
import com.del.testandroiddeveloper.domain.repository.IMovieRepository
import com.del.testandroiddeveloper.utlis.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository (
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
): IMovieRepository{
    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : RemoteBoundSource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getMovies()
            }

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getComingSoonMovie(): Flow<Resource<List<Movie>>> =
        object : RemoteBoundSource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getComingSoonMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getComingSoonMovie()
            }

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapMovieComingSoonResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getSearchMovies(search: String): Flow<List<Movie>> {
        return localDataSource.getMovieSearch(search).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getSearchFavoriteMovies(search: String): Flow<List<Movie>> {
        return localDataSource.getMovieFavoriteSearch(search).map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getAllFavoriteMovies().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }



    override suspend fun setMovieFavorite(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntity(movie)
        return localDataSource.setMovieFavorite(movieEntity, state)
    }
}