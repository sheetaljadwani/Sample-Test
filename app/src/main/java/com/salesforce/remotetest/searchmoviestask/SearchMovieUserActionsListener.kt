package com.salesforce.remotetest.searchmoviestask


import com.salesforce.remotetest.data.Movie

/**
 * Listener used with data binding to process user actions.
 */
interface SearchMovieUserActionsListener {
    fun onMovieClicked(movie: Movie)
}
