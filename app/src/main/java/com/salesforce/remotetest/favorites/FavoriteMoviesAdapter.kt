package com.salesforce.remotetest.favorites

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.salesforce.remotetest.R
import com.salesforce.remotetest.data.Movie
import com.salesforce.remotetest.searchmoviestask.SearchMovieUserActionsListener
import kotlinx.android.synthetic.main.search_movie_item.view.*
import com.salesforce.remotetest.databinding.FavMovieItemBinding
import kotlinx.android.synthetic.main.fav_movie_item.view.*


class FavoriteMoviesAdapter(
    private val activity: FragmentActivity,
    private var movies: List<Movie>,
    private val favoriteMoviesViewModel: FavoriteMoviesViewModel
): RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemViewHolder(activity, parent)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is ItemViewHolder && movies.size > position) {
            holder.bind(movies[position])
        }
    }

    override fun getItemViewType(position: Int) = 1
    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class ItemViewHolder(
        private val activity: FragmentActivity,
        private val parent: ViewGroup,
        private val binding: FavMovieItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.fav_movie_item,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            Glide.with(activity).load(movie.poster).transition(
                DrawableTransitionOptions.withCrossFade()).into(itemView.fav_poster_image)
            binding.listener = object : SearchMovieUserActionsListener {
                override fun onMovieClicked(movie: Movie) {
                    showMovieDetails(movie)
                }
            }
        }

        private fun showMovieDetails(movie: Movie) {
            val alertDialog = AlertDialog.Builder(activity).apply {
                setTitle(movie.title)
                setMessage(movie.director ?: movie.year)
                setNeutralButton("Wonderful") { dialog, which -> dialog.cancel() }
            }
            val dialog = alertDialog.create()
            dialog.show()
        }
    }

    fun replaceData(movies: List<Movie>) {
        setList(movies)
    }

    private fun setList(movies: List<Movie>) {
        Log.v("SearchAdapter" , "Movies: $movies")
        this.movies = movies
        notifyDataSetChanged()
    }
}