package com.salesforce.remotetest.searchmoviestask

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.salesforce.remotetest.data.Movie

/**
 * Contains [BindingAdapter] for the [Movie] list.
 */
object SearchMoviesListBindings {

    @BindingAdapter("app:items")
    @JvmStatic fun setItems(listView: RecyclerView, items: List<Movie>?) {
        with(listView.adapter as SearchMoviesAdapter?) {
            items?.let {
                this?.replaceData(items)
            }
        }
    }
}
