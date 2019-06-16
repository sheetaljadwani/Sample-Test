package com.salesforce.remotetest.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.salesforce.remotetest.data.Movie

/**
 * Data Access Object for the movies table.
 */
@Dao interface MovieDao {

    /**
     * Select all movies from the movies table.
     *
     * @return all movies.
     */
    @Query("SELECT * FROM Movie WHERE Title LIKE :searchMovie") fun getMovies(searchMovie: String): List<Movie>

    /**
     * Select a movie by id.
     *
     * @param id the movie id.
     * @return the movie with id.
     */
    @Query("SELECT * FROM Movie WHERE entryid = :id") fun getMovieById(id: String): Movie?

    /**
     * Insert a movie in the database. If the movie already exists, replace it.
     *
     * @param movie the movie to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insertMovie(movie: Movie)

    /**
     * Select a movie by favorite.
     *
     * @return all movies
     */
    @Query("SELECT * FROM Movie WHERE favorite = 1")fun getFavoriteMovie(): List<Movie>

    /**
     * Update a movie.
     *
     * @param movie movie to be updated
     * @return the number of movies updated. This should always be 1.
     */
    @Update fun updateMovie(movie: Movie): Int

    /**
     * Update the complete status of a movie
     *
     * @param id    id of the movie
     * @param favorite favorite status to be updated
     */
    @Query("UPDATE Movie SET favorite = :favorite WHERE entryid = :id")
    fun updateFavorite(id: String, favorite: Int)

    /**
     * Delete a movie by id.
     *
     * @return the number of movies deleted. This should always be 1.
     */
    @Query("DELETE FROM Movie WHERE entryid = :movieId") fun deleteMovieById(movieId: String): Int

    /**
     * Delete all movies.
     */
    @Query("DELETE FROM Movie") fun deleteMovie()

    /**
     * Delete all favorite movies from the table.
     *
     * @return the number of movies deleted.
     */
    @Query("DELETE FROM Movie WHERE favorite = 1") fun deleteFavoriteMovie(): Int
}