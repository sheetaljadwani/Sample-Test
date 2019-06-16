package com.salesforce.remotetest.data.source

import com.salesforce.remotetest.data.Movie

interface MoviesDataSource {

    interface LoadMoviesCallback {

        fun onMoviesLoaded(movies: List<Movie>)

        fun onDataNotAvailable()
    }

    interface GetMovieCallback {

        fun onMovieLoaded(movie: Movie)

        fun onDataNotAvailable()
    }

    interface ClearMovieCallback {

        fun onMoviesCleared()

        fun onDataNotAvailable()
    }

    interface ClearFavoriteMoviesCallback {

        fun onFavoriteMoviesCleared()

        fun onDataNotAvailable()
    }

    interface DeleteMovieCallback {

        fun onMovieDeleted()

        fun onDataNotAvailable()
    }


    fun getMovies(
        searchMovies: String,
        callback: LoadMoviesCallback
    )

    fun getMovie(movieTitle: String, callback: GetMovieCallback)

    fun getFavoriteMovies(callback: LoadMoviesCallback)

    fun setMovieAsFavorite(
        movie: Movie,
        callback: GetMovieCallback
    )

    fun setMovieAsUnfavorite(
        movie: Movie,
        callback: GetMovieCallback
    )

    fun clearFavorites(callback: ClearFavoriteMoviesCallback)

    fun refreshMovies(callback: LoadMoviesCallback)

    fun saveMovieLocally(
        movie: Movie,
        callback: GetMovieCallback
    )

    fun deleteAllLocalMovies(callback: ClearMovieCallback)

    fun deleteLocalMovie(
        movieId: String,
        callback: DeleteMovieCallback
    )

}