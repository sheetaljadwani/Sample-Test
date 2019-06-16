package com.salesforce.remotetest.data

import androidx.annotation.VisibleForTesting
import com.salesforce.remotetest.data.source.MoviesDataSource
import com.google.common.collect.Lists
import java.util.*

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
object FakeMoviesRemoteDataSource : MoviesDataSource {
    private var MOVIES_SERVICE_DATA: LinkedHashMap<String, Movie> = LinkedHashMap()

    override fun getMovies(
        searchMovies: String,
        callback: MoviesDataSource.LoadMoviesCallback
    ) {
        callback.onMoviesLoaded(Lists.newArrayList(MOVIES_SERVICE_DATA.values))
    }

    override fun getMovie(movieId: String, callback: MoviesDataSource.GetMovieCallback) {
        val movie = MOVIES_SERVICE_DATA[movieId]
        movie?.let { callback.onMovieLoaded(it) }
    }

    override fun refreshMovies(callback: MoviesDataSource.LoadMoviesCallback) {
        //TODO
    }
    override fun setMovieAsFavorite(movie: Movie, callback: MoviesDataSource.GetMovieCallback) {
        //TODO
    }

    override fun setMovieAsUnfavorite(movie: Movie, callback: MoviesDataSource.GetMovieCallback) {
        //TODO
    }

    override fun clearFavorites(callback: MoviesDataSource.ClearFavoriteMoviesCallback) {
        //TODO
    }

    override fun saveMovieLocally(movie: Movie, callback: MoviesDataSource.GetMovieCallback) {
        //TODO
    }

    override fun deleteAllLocalMovies(callback: MoviesDataSource.ClearMovieCallback) {
        //TODO
    }

    override fun deleteLocalMovie(movieId: String, callback: MoviesDataSource.DeleteMovieCallback) {
        //TODO
    }

    override fun getFavoriteMovies(callback: MoviesDataSource.LoadMoviesCallback) {
        //TODO
    }

    @VisibleForTesting
    fun addMovies(vararg movies: Movie) {
        for (movie in movies) {
            MOVIES_SERVICE_DATA.put(movie.id, movie)
        }
    }
}
