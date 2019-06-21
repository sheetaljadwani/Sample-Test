package com.salesforce.remotetest.di

import com.salesforce.remotetest.api.OMDbApiService
import com.salesforce.remotetest.data.source.MoviesRepository
import com.salesforce.remotetest.data.source.local.MovieDao
import com.salesforce.remotetest.data.source.local.MoviesLocalDataSource
import com.salesforce.remotetest.data.source.remote.MoviesRemoteDataSource
import com.salesforce.remotetest.util.AppExecutors
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class, RxJavaModule::class])
class RepositoryModule {

    @Provides
    @Singleton
    fun provideMoviesRepository(omDbApiService: OMDbApiService,
                                @Named(SUBCRIBER_ON) subscribeOn: Scheduler,
                                @Named(OBSERVER_ON) observeOn: Scheduler,
                                movieDao: MovieDao) =
        MoviesRepository.getInstance(MoviesRemoteDataSource.getInstance(omDbApiService, subscribeOn, observeOn),
        MoviesLocalDataSource.getInstance(movieDao, AppExecutors()))

}