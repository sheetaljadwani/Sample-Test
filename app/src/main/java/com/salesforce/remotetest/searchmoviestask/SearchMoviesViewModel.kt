package com.salesforce.remotetest.searchmoviestask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.salesforce.remotetest.base.BaseViewModel
import com.salesforce.remotetest.data.Movie
import com.salesforce.remotetest.data.source.MoviesDataSource
import com.salesforce.remotetest.data.source.MoviesRepository
import java.util.ArrayList
import javax.inject.Inject

class SearchMoviesViewModel@Inject constructor(private val moviesRepository: MoviesRepository): BaseViewModel() {

    private val _items = MutableLiveData<List<Movie>>()
    val items: LiveData<List<Movie>>
        get() = _items

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    private val _noMoviesLabel = MutableLiveData<Int>()
    val noMoviesLabel: LiveData<Int>
        get() = _noMoviesLabel

    private val isDataLoadingError = MutableLiveData<Boolean>()

    private val _searchMovies = MutableLiveData<String>()
    val searchMovies: LiveData<String>
        get() = _searchMovies

    val empty: LiveData<Boolean> = Transformations.map(_items) {
        it.isEmpty()
    }

    fun getMovies(searchMovie: String, forceUpdate: Boolean) {
        getMovies(searchMovie, forceUpdate, true)
    }


    fun getMovies(searchMovie: String, forceUpdate: Boolean, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            _dataLoading.setValue(true)
        }
        
        moviesRepository.getMovies(searchMovie, object : MoviesDataSource.LoadMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>) {
                Log.v("SearchViewModel" , "Before Movies: $movies")
                if (showLoadingUI) {
                    _dataLoading.value = false
                }
                isDataLoadingError.value = false
                _items.value = movies
                Log.v("SearchViewModel" , "Movies: ${_items.value}")
            }

            override fun onDataNotAvailable() {
                isDataLoadingError.value = true
            }
        })
    }

    fun getMovie(movie: Movie) {
        getMovie(movie, true)
    }

    fun getMovie(movie: Movie, showLoadingUI: Boolean) {
        if (showLoadingUI) {
            _dataLoading.setValue(true)
        }

        moviesRepository.getMovie(movie.title, object : MoviesDataSource.GetMovieCallback {
            override fun onMovieLoaded(movie: Movie) {
                if (showLoadingUI) {
                    _dataLoading.value = false
                }
                isDataLoadingError.value = false
//                _items.value = movie
            }

            override fun onDataNotAvailable() {
                isDataLoadingError.value = true
            }
        })
    }

    fun onRefresh(searchMovie: String) {
        getMovies(searchMovie, false)
    }

}