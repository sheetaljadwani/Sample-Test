package com.salesforce.remotetest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Immutable model class for a Movie.
 *
 */
@Entity(tableName = "Movie")
data class Movie (
    @ColumnInfo(name = "Title") var title: String = "",
    @ColumnInfo(name = "Year") var year: String? = "",
    @ColumnInfo(name = "Rated") var rated: String? = "",
    @ColumnInfo(name = "Released") var released: String? = "",
    @ColumnInfo(name = "Runtime") var runtime: String? = "",
    @ColumnInfo(name = "Genre") var genre: String? = "",
    @ColumnInfo(name = "Director") var director: String? = "",
    @ColumnInfo(name = "Writer") var writer: String? = "",
    @ColumnInfo(name = "Actors") var actors: String? = "",
    @ColumnInfo(name = "Plot") var plot: String? = "",
    @ColumnInfo(name = "Awards") var awards: String? = "",
    @ColumnInfo(name = "Poster") var poster: String? = "",
    @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()
) {
    /**
     * True if movie has been set as favorite
     */
    @ColumnInfo(name = "favorite") var favorite = 0

}