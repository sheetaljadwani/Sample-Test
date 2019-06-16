package com.salesforce.remotetest.di

import com.salesforce.remotetest.data.source.remote.MoviesRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesRepository() = MoviesRemoteDataSource.getInstance()

}