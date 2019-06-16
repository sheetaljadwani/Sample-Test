package com.salesforce.remotetest.data.source.remote

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.VisibleForTesting
import com.salesforce.remotetest.api.OMDbApiService
import com.salesforce.remotetest.data.Movie
import com.salesforce.remotetest.data.Response
import com.salesforce.remotetest.data.source.MoviesDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MoviesRemoteDataSource:  MoviesDataSource {

    private val omDbApiService by lazy {
        val omDbApiService: OMDbApiService = OMDbApiService.create()
        omDbApiService
    }

    lateinit var disposable: Disposable

    @SuppressLint("CheckResult")
    override fun getMovies(searchMovie: String, callback: MoviesDataSource.LoadMoviesCallback) {
        var movies = listOf<Movie>()
        disposable = omDbApiService.getMovies(searchMovie)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableObserver<Response>() {
                override fun onError(e: Throwable) {
                    callback.onDataNotAvailable()
                }

                override fun onNext(response: Response) {
                    movies = response.getMoiesFromSearch()
                }

                override fun onComplete() {
                    Log.v("SearchRemoteDataSource" , "Movies: $movies")
                    callback.onMoviesLoaded(movies)
                }
            })
    }

    override fun getMovie(movieTitle: String, callback: MoviesDataSource.GetMovieCallback) {
        var movie = Movie()
        disposable = omDbApiService.getMovie(movieTitle)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnComplete { callback.onMovieLoaded(movie) }
            .subscribeWith(object: DisposableObserver<Movie>() {
                override fun onError(e: Throwable) {
                    callback.onDataNotAvailable()
                }

                override fun onNext(_movie: Movie) {
                    movie = _movie
                }

                override fun onComplete() {
                    callback.onMovieLoaded(movie)
                }
            })
    }

    override fun setMovieAsFavorite(
        movie: Movie,
        callback: MoviesDataSource.GetMovieCallback
    ) {

    }

    override fun setMovieAsUnfavorite(
        movie: Movie,
        callback: MoviesDataSource.GetMovieCallback
    ) {

    }

    override fun clearFavorites(callback: MoviesDataSource.ClearFavoriteMoviesCallback) {

    }

    override fun refreshMovies(callback: MoviesDataSource.LoadMoviesCallback) {

    }

    override fun saveMovieLocally(
        movie: Movie,
        callback: MoviesDataSource.GetMovieCallback
    ) {

    }

    override fun deleteAllLocalMovies(callback: MoviesDataSource.ClearMovieCallback) {

    }

    override fun deleteLocalMovie(
        movieId: String,
        callback: MoviesDataSource.DeleteMovieCallback
    ) {

    }

    override fun getFavoriteMovies(callback: MoviesDataSource.LoadMoviesCallback) {

    }



    companion object {
        private var INSTANCE:  MoviesRemoteDataSource? = null

        @JvmStatic
        fun getInstance(): MoviesRemoteDataSource {
            if (INSTANCE == null) {
                synchronized(MoviesRemoteDataSource::javaClass) {
                    INSTANCE =  MoviesRemoteDataSource()
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun clearInstance() {
            INSTANCE = null
        }
    }
}