package com.del.testandroiddeveloper.ui.favrotie

import androidx.lifecycle.*
import com.del.testandroiddeveloper.domain.model.Movie
import com.del.testandroiddeveloper.domain.usecase.MovieAppUseCase

class FavoriteViewModel(private val movieUseCase: MovieAppUseCase) : ViewModel() {
    private var title: MutableLiveData<String> = MutableLiveData()

    fun getFavoriteMovie(): LiveData<List<Movie>> =
        movieUseCase.getFavoriteMovies().asLiveData()

    fun setSearch(query: String) {
        if (title.value == query) {
            return
        }
        title.value = query
    }

    val movie: LiveData<List<Movie>> = Transformations.switchMap(title){
        movieUseCase.getSearchFavoriteMovies(it).asLiveData()
    }
}