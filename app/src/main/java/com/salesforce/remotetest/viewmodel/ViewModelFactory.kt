package com.salesforce.remotetest

import android.annotation.SuppressLint
import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salesforce.remotetest.base.BaseApplication
import com.salesforce.remotetest.data.source.MoviesRepository
import com.salesforce.remotetest.favorites.FavoriteMoviesViewModel
import com.salesforce.remotetest.searchmoviestask.SearchMoviesViewModel
import javax.inject.Inject

class ViewModelFactory (private val moviesRepository: MoviesRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(SearchMoviesViewModel::class.java) ->
                        SearchMoviesViewModel(moviesRepository)
                    isAssignableFrom(FavoriteMoviesViewModel::class.java) ->
                        FavoriteMoviesViewModel(moviesRepository)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T

    companion object {

        @SuppressLint("StaticFieldLeak")
        @Volatile private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: BaseApplication) =
                INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                    INSTANCE ?: ViewModelFactory(application.moviesRepository)
                            .also { INSTANCE = it }
                }


        @VisibleForTesting fun destroyInstance() {
            INSTANCE = null
        }

    }
}
