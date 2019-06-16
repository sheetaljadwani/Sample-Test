package com.salesforce.remotetest.data.source.local

import android.util.Log
import androidx.annotation.VisibleForTesting
import com.salesforce.remotetest.data.Movie
import com.salesforce.remotetest.data.source.MoviesDataSource
import com.salesforce.remotetest.util.AppExecutors

class MoviesLocalDataSource private constructor(
    val appExecutors: AppExecutors,
    val moviesDao: MovieDao) : MoviesDataSource {
    override fun getMovies(
        searchMovies: String,
        callback: MoviesDataSource.LoadMoviesCallback
    ) {
        appExecutors.diskIO.execute {
            val movies = moviesDao.getMovies(searchMovies)
            appExecutors.mainThread.execute {
                if (movies.isEmpty()) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onMoviesLoaded(movies)
                }
            }
        }
    }

    override fun getMovie(movieTitle: String, callback: MoviesDataSource.GetMovieCallback) {
        appExecutors.diskIO.execute {
            val movie = moviesDao.getMovieById(movieTitle)
            appExecutors.mainThread.execute {
                if (movie != null) {
                    callback.onMovieLoaded(movie)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun getFavoriteMovies(callback: MoviesDataSource.LoadMoviesCallback) {
        appExecutors.diskIO.execute {
            val movie = moviesDao.getFavoriteMovie()
            appExecutors.mainThread.execute {
                if (movie != null) {
                    Log.v("moviesLocalDataSource", "getFavoriteMovies: $movie")
                    callback.onMoviesLoaded(movie)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun setMovieAsFavorite(
        movie: Movie,
        callback: MoviesDataSource.GetMovieCallback
    ) {
        appExecutors.diskIO.execute {
            movie.favorite = 1
            val result = moviesDao.updateFavorite(movie.id, 1)
            appExecutors.mainThread.execute {
                if (result != null) {
                    callback.onMovieLoaded(movie)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun setMovieAsUnfavorite(
        movie: Movie,
        callback: MoviesDataSource.GetMovieCallback
    ) {
        appExecutors.diskIO.execute {
            movie.favorite = 0
            val result = moviesDao.updateFavorite(movie.id, 0)
            appExecutors.mainThread.execute {
                if (result != null) {
                    callback.onMovieLoaded(movie)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun clearFavorites(callback: MoviesDataSource.ClearFavoriteMoviesCallback) {
        appExecutors.diskIO.execute {
            val result = moviesDao.deleteMovie()
            appExecutors.mainThread.execute {
                if (result != null) {
                    callback.onFavoriteMoviesCleared()
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun refreshMovies(callback: MoviesDataSource.LoadMoviesCallback) {
    }

    override fun saveMovieLocally(
        movie: Movie,
        callback: MoviesDataSource.GetMovieCallback
    ) {
        appExecutors.diskIO.execute {
            val result = moviesDao.insertMovie(movie)
            appExecutors.mainThread.execute {
                if (result != null) {
                    callback.onMovieLoaded(movie)
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun deleteAllLocalMovies(callback: MoviesDataSource.ClearMovieCallback) {
        appExecutors.diskIO.execute {
            val result = moviesDao.deleteMovie()
            appExecutors.mainThread.execute {
                if (result != null) {
                    callback.onMoviesCleared()
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    override fun deleteLocalMovie(
        movieTitle: String,
        callback: MoviesDataSource.DeleteMovieCallback
    ) {
        appExecutors.diskIO.execute {
            val result = moviesDao.deleteMovieById(movieTitle)
            appExecutors.mainThread.execute {
                if (result != null) {
                    callback.onMovieDeleted()
                } else {
                    callback.onDataNotAvailable()
                }
            }
        }
    }

    companion object {
        private var INSTANCE:  MoviesLocalDataSource? = null

        @JvmStatic
        fun getInstance(appExecutors: AppExecutors, moviesDao: MovieDao): MoviesLocalDataSource {
            if (INSTANCE == null) {
                synchronized(MoviesLocalDataSource::javaClass) {
                    INSTANCE =  MoviesLocalDataSource(appExecutors, moviesDao)
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