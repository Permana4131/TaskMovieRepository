package com.del.testandroiddeveloper.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.del.testandroiddeveloper.domain.model.Movie
import com.del.testandroiddeveloper.domain.usecase.MovieAppUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val movieAppUseCase: MovieAppUseCase) : ViewModel() {

    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) = viewModelScope.launch {
        movieAppUseCase.setMovieFavorite(movie, newStatus)
    }
 }