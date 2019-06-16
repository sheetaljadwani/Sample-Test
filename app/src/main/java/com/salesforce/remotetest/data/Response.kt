package com.salesforce.remotetest.data

/**
 * Immutable model class for a Movie.
 *
 */
data class Response (val Search: Array<Search>, val Response: String, val totalResult: String){

    val _search: MutableList<Search>
        get() = Search?.toMutableList()

    fun getMoiesFromSearch() : MutableList<Movie> {
        val movies = ArrayList<Movie>()
        _search?.forEach{
            movies.add(Movie(it.Title, it.Year, poster = it.Poster ))
        }
        return movies
    }
}