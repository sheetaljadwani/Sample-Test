package com.salesforce.remotetest.searchmoviestask

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
import com.salesforce.remotetest.MainActivity
import com.salesforce.remotetest.R
import com.salesforce.remotetest.data.Movie
import com.salesforce.remotetest.databinding.SearchMovieItemBinding
import com.salesforce.remotetest.favorites.FavoriteMovieUserActionsListener
import com.salesforce.remotetest.favorites.FavoriteMoviesViewModel
import com.salesforce.remotetest.obtainViewModel
import kotlinx.android.synthetic.main.search_movie_item.view.*


class SearchMoviesAdapter(
    private var activity : FragmentActivity,
    private var movies: List<Movie>,
    private val searchMoviesViewModel: SearchMoviesViewModel
): RecyclerView.Adapter<SearchMoviesAdapter.ViewHolder>() {

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
        private val binding: SearchMovieItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.search_movie_item,
            parent,
            false
        )
    ) : ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.favoriteViewmodel = (activity as MainActivity).obtainViewModel(FavoriteMoviesViewModel::class.java)
            Glide.with(activity).load(movie.poster).transition(
                DrawableTransitionOptions.withCrossFade()).into(this.itemView.poster_image)
            if(movie.favorite == 1) {
                itemView.favorite_image_view.setBackgroundResource(com.salesforce.remotetest.R.drawable.ic_favorite_movie)
            } else {
                itemView.favorite_image_view.setBackgroundResource(com.salesforce.remotetest.R.drawable.ic_unfavorite_movie)
            }
            binding.listener = object : SearchMovieUserActionsListener{
                override fun onMovieClicked(movie: Movie) {
                    showMovieDetail(movie)
                }
            }

            binding.favListener = object : FavoriteMovieUserActionsListener{
                override fun onMovieFavorited(movie: Movie, favorite: Boolean) {
                    if(favorite) {
                        movie.favorite = 1
                        itemView.favorite_image_view.setBackgroundResource(R.drawable.ic_favorite_movie)
                    } else {
                        movie.favorite = 0
                        itemView.favorite_image_view.setBackgroundResource(R.drawable.ic_unfavorite_movie)
                    }
                    binding.favoriteViewmodel?.setMovieFavorite(movie, favorite)
                }
            }
        }

        private fun showMovieDetail(movie: Movie) {
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