package com.salesforce.remotetest.data.source

import android.util.Log
import com.salesforce.remotetest.data.Movie
import com.salesforce.remotetest.util.EspressoIdlingResource
import java.util.*

class MoviesRepository(
        val moviesRemoteDataSource: MoviesDataSource,
        val moviesLocalDataSource: MoviesDataSource
) : MoviesDataSource {


    var cachedMovies: LinkedHashMap<String, Movie> = LinkedHashMap()
    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    var cacheIsDirty = false

    /**
     * Gets movies from cache, local data source (SQLite) or remote data source, whichever is
     * available first.
     *
     *
     * Note: [LoadMoviesCallback.onDataNotAvailable] is fired if all data sources fail to
     * get the data.
     */
    override fun getMovies(
        searchMovies: String,
        callback: MoviesDataSource.LoadMoviesCallback
    ) {
        Log.v("SearchRepo", "cacheIsDirty $cacheIsDirty")
        EspressoIdlingResource.increment()
        if(!cacheIsDirty) {
            getMoviesFromRemoteDataSource(searchMovies, callback)
        } else {
            val searchQuery = "%{$searchMovies}%"
            moviesLocalDataSource.getMovies(searchQuery, object : MoviesDataSource.LoadMoviesCallback {
                override fun onMoviesLoaded(movies: List<Movie>) {
                    refreshCache(movies)
                    EspressoIdlingResource.decrement()
                    callback.onMoviesLoaded(ArrayList(movies))
                }

                override fun onDataNotAvailable() {
                    getMoviesFromRemoteDataSource(searchMovies, callback)
                }
            })
        }
    }

    override fun setMovieAsFavorite(
        movie: Movie,
        callback: MoviesDataSource.GetMovieCallback
    ) {
        movie.favorite = 1
        moviesLocalDataSource.setMovieAsFavorite(movie, callback)
    }

    override fun setMovieAsUnfavorite(
        movie: Movie,
        callback: MoviesDataSource.GetMovieCallback
    ) {
        movie.favorite = 1
        moviesLocalDataSource.setMovieAsFavorite(movie, callback)
    }

    /**
     * Gets movie from local data source (sqlite) unless the table is new or empty. In that case it
     * uses the network data source. This is done to simplify the sample.
     *
     *
     * Note: [GetMovieCallback.onDataNotAvailable] is fired if both data sources fail to
     * get the data.
     */
    override fun getMovie(movieTitle: String, callback: MoviesDataSource.GetMovieCallback) {

        EspressoIdlingResource.increment()

        moviesLocalDataSource.getMovie(movieTitle, object : MoviesDataSource.GetMovieCallback {
            override fun onMovieLoaded(movie: Movie) {
                    EspressoIdlingResource.decrement()
                    callback.onMovieLoaded(movie)
            }

            override fun onDataNotAvailable() {
                moviesRemoteDataSource.getMovie(movieTitle, object : MoviesDataSource.GetMovieCallback {
                    override fun onMovieLoaded(movie: Movie) {
                            EspressoIdlingResource.decrement()
                            callback.onMovieLoaded(movie)
                    }

                    override fun onDataNotAvailable() {
                        EspressoIdlingResource.decrement()
                        callback.onDataNotAvailable()
                    }
                })
            }
        })
    }

    override fun clearFavorites(callback: MoviesDataSource.ClearFavoriteMoviesCallback) {
        moviesLocalDataSource.clearFavorites(callback)
    }

    override fun refreshMovies(callback: MoviesDataSource.LoadMoviesCallback) {
        cacheIsDirty = false
    }

    private fun getMoviesFromRemoteDataSource(searchMovies: String,callback: MoviesDataSource.LoadMoviesCallback) {
        moviesRemoteDataSource.getMovies(searchMovies, object : MoviesDataSource.LoadMoviesCallback {
            override fun onMoviesLoaded(movies: List<Movie>) {
                movies.forEach{ Log.i("getMovies:" , it.title )}
                refreshLocalDataSource(movies, callback)
                EspressoIdlingResource.decrement()
                callback.onMoviesLoaded(movies)
            }

            override fun onDataNotAvailable() {
                EspressoIdlingResource.decrement()
                callback.onDataNotAvailable()
            }
        })
    }

    private fun refreshLocalDataSource(movies: List<Movie>, callback: MoviesDataSource.LoadMoviesCallback) {
        moviesLocalDataSource.deleteAllLocalMovies( object : MoviesDataSource.ClearMovieCallback{
            override fun onMoviesCleared() {
                for (movie in movies) {
                    moviesLocalDataSource.saveMovieLocally(movie, object : MoviesDataSource.GetMovieCallback{
                        override fun onMovieLoaded(movie: Movie) {

                        }

                        override fun onDataNotAvailable() {
                            callback.onDataNotAvailable()
                        }
                    })
                    cacheIsDirty = true
                }
                callback.onMoviesLoaded(movies)
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun deleteAllLocalMovies(callback: MoviesDataSource.ClearMovieCallback) {
        moviesLocalDataSource.deleteAllLocalMovies(callback)
    }

    override fun deleteLocalMovie(
        movieId: String,
        callback: MoviesDataSource.DeleteMovieCallback
    ) {
        moviesLocalDataSource.deleteLocalMovie(movieId, callback)
    }

    override fun saveMovieLocally(
        movie: Movie,
        callback: MoviesDataSource.GetMovieCallback
    ) {
        moviesLocalDataSource.saveMovieLocally(movie, callback)
    }

    override fun getFavoriteMovies(callback: MoviesDataSource.LoadMoviesCallback) {
        moviesLocalDataSource.getFavoriteMovies(callback)
    }

    private fun refreshCache(tasks: List<Movie>) {
        cachedMovies.clear()
        tasks.forEach {
            cacheAndPerform(it)
        }
    }

    private inline fun cacheAndPerform(movie: Movie) {
        val cachedMovie = Movie(title = movie.title, director = movie.director, id = movie.id).apply {
            favorite = movie.favorite
        }
        cachedMovies.put(cachedMovie.id, cachedMovie)
    }


    companion object {

        private var INSTANCE: MoviesRepository? = null

        /**
         * Returns the single instance of this class, creating it if necessary.

         * @param moviesRemoteDataSource the backend data source
         * *
         * @param moviesLocalDataSource  the device storage data source
         * *
         * @return the [MoviesRepository] instance
         */
        @JvmStatic fun getInstance(moviesRemoteDataSource: MoviesDataSource,
                moviesLocalDataSource: MoviesDataSource) =
                INSTANCE ?: synchronized(MoviesRepository::class.java) {
                    INSTANCE ?: MoviesRepository(moviesRemoteDataSource, moviesLocalDataSource)
                            .also { INSTANCE = it }
                }


        /**
         * Used to force [getInstance] to create a new instance
         * next time it's called.
         */
        @JvmStatic fun destroyInstance() {
            INSTANCE = null
        }
    }
}
