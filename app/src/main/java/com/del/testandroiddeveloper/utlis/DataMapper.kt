package com.del.testandroiddeveloper.utlis

import com.del.testandroiddeveloper.data.local.entity.MovieEntity
import com.del.testandroiddeveloper.data.remote.response.MovieResponse
import com.del.testandroiddeveloper.domain.model.Movie

object DataMapper {
    fun mapMovieResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                it.overview,
                it.originalLanguage,
                it.releaseDate,
                it.popularity,
                it.voteAverage,
                it.id,
                it.title,
                it.voteCount,
                it.posterPath,
                it.backdropPath,
                favorite = false,
                isComingSoon = false
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapMovieComingSoonResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            if (it.posterPath==null){
                val movie = MovieEntity(
                    it.overview,
                    it.originalLanguage,
                    it.releaseDate,
                    it.popularity,
                    it.voteAverage,
                    it.id,
                    it.title,
                    it.voteCount,
                    posterPath = "null",
                    backdropPath = "null",
                    favorite = false,
                    isComingSoon = true
                )
                movieList.add(movie)
            }else{
                val movie = MovieEntity(
                    it.overview,
                    it.originalLanguage,
                    it.releaseDate,
                    it.popularity,
                    it.voteAverage,
                    it.id,
                    it.title,
                    it.voteCount,
                    it.posterPath,
                    backdropPath = "null",
                    favorite = false,
                    isComingSoon = true
                )
                movieList.add(movie)
            }


        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> {
        return input.map {
            Movie(
                it.overview,
                it.originalLanguage,
                it.releaseDate,
                it.popularity,
                it.voteAverage,
                it.id,
                it.title,
                it.voteCount,
                it.posterPath,
                it.backdropPath,
                favorite = it.favorite,
                isComingSoon = it.isComingSoon
            )
        }
    }

    fun mapDomainToEntity(input: Movie): MovieEntity {
        return MovieEntity(
            input.overview,
            input.originalLanguage,
            input.releaseDate,
            input.popularity,
            input.voteAverage,
            input.id,
            input.title,
            input.voteCount,
            input.posterPath,
            input.backdropPath,
            favorite = input.favorite,
            isComingSoon = input.isComingSoon
        )
    }
}