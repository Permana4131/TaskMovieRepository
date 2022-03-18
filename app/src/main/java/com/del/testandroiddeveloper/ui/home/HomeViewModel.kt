package com.del.testandroiddeveloper.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.del.testandroiddeveloper.data.Resource
import com.del.testandroiddeveloper.domain.model.Movie
import com.del.testandroiddeveloper.domain.usecase.MovieAppUseCase

class HomeViewModel(private val movieUseCase: MovieAppUseCase) : ViewModel() {
    fun getPopularMovie(): LiveData<Resource<List<Movie>>> =
        movieUseCase.getAllMovies().asLiveData()

    fun getComingSoonMovie(): LiveData<Resource<List<Movie>>> =
        movieUseCase.getComingSoonMovie().asLiveData()
}