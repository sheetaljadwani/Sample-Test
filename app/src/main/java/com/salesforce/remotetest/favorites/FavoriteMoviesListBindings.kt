package com.salesforce.remotetest.favorites

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salesforce.remotetest.data.Movie

/**
 * Contains [BindingAdapter] for the [Movie] list.
 */
object FavoriteMoviesListBindings {

    @BindingAdapter("app:favItems")
    @JvmStatic fun setFavItems(listView: RecyclerView, items: List<Movie>?) {
        with(listView.adapter as FavoriteMoviesAdapter?) {
            items?.let {
                this?.replaceData(items)
            }
        }
    }
}
