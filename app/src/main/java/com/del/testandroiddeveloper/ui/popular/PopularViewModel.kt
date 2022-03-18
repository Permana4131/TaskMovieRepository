package com.del.testandroiddeveloper.ui.popular

import androidx.lifecycle.*
import com.del.testandroiddeveloper.data.Resource
import com.del.testandroiddeveloper.domain.model.Movie
import com.del.testandroiddeveloper.domain.usecase.MovieAppUseCase

class PopularViewModel (private val movieUseCase: MovieAppUseCase) : ViewModel() {
    private var title: MutableLiveData<String> = MutableLiveData()

    fun getPopularMovie(): LiveData<Resource<List<Movie>>> =
        movieUseCase.getAllMovies().asLiveData()


    fun setSearch(query: String) {
        if (title.value == query) {
            return
        }
        title.value = query
    }

    val movie: LiveData<List<Movie>> = Transformations.switchMap(title){
        movieUseCase.getSearchMovies(it).asLiveData()
    }
}