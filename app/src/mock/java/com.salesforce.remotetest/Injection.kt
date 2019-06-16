package com.salesforce.remotetest

import android.content.Context
import com.salesforce.remotetest.data.FakeMoviesRemoteDataSource
import com.salesforce.remotetest.data.source.MoviesRepository
import com.salesforce.remotetest.data.source.local.MoviesDatabase
import com.salesforce.remotetest.data.source.local.MoviesLocalDataSource
import com.salesforce.remotetest.util.AppExecutors

/**
 * Enables injection of production implementations for
 * [Movie] at compile time.
 */
object Injection {

    fun provideMoviesRepository(context: Context): MoviesRepository {
        val database = MoviesDatabase.getInstance(context)
        return MoviesRepository.getInstance(FakeMoviesRemoteDataSource,
                MoviesLocalDataSource.getInstance(AppExecutors(), database.movieDao()))
    }
}
