package com.salesforce.remotetest.favorites


import com.salesforce.remotetest.data.Movie

/**
 * Listener used with data binding to process user actions.
 */
interface FavoriteMovieUserActionsListener {
    fun onMovieFavorited(movie: Movie, favorite : Boolean)
}
