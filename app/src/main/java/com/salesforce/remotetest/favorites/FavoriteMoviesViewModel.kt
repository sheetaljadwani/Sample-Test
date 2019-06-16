package com.salesforce.remotetest.favorites

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

class FavoriteMoviesViewModel@Inject constructor(private val moviesRepository: MoviesRepository): BaseViewModel() {
    private val _items = MutableLiveData<List<Movie>>().apply { value = emptyList() }
    val favItems: LiveData<List<Movie>>
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

    fun getFavoriteMovies(showLoadingUI: Boolean) {
        if (showLoadingUI) {
            _dataLoading.setValue(true)
        }

        moviesRepository.moviesLocalDataSource.getFavoriteMovies(object : MoviesDataSource.LoadMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>) {
                Log.v("moviesLocalDataSource", "getFavoriteMovies: $movies")
                if (showLoadingUI) {
                    _dataLoading.value = false
                }
                isDataLoadingError.value = false
                _items.value = movies
            }

            override fun onDataNotAvailable() {
                isDataLoadingError.value = true
            }
        })
    }

    fun setMovieFavorite(movie: Movie, favorite: Boolean) {
        if(favorite) {
            moviesRepository.moviesLocalDataSource.setMovieAsFavorite(movie, object : MoviesDataSource.GetMovieCallback {
                override fun onMovieLoaded(movies: Movie) {
                    Log.v("moviesLocalDataSource", "setMovieFavorite: $movie")
                }

                override fun onDataNotAvailable() {
                    isDataLoadingError.value = true
                }
            })
        } else {
            moviesRepository.moviesLocalDataSource.setMovieAsUnfavorite(movie, object : MoviesDataSource.GetMovieCallback {
                override fun onMovieLoaded(movies: Movie) {
                    Log.v("moviesLocalDataSource", "setMovieUnFavorite: $favorite")
                }

                override fun onDataNotAvailable() {
                    isDataLoadingError.value = true
                }
            })
        }
    }

    fun onRefresh() {
        getFavoriteMovies(true)
    }
}