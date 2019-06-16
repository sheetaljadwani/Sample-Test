package com.salesforce.remotetest.data

/**
 * Immutable model class for a Movie.
 *
 */
data class Search (val Type: String, val Year: String, val imdbID: String, val Poster: String? = null, val Title: String)