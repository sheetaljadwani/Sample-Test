package com.salesforce.remotetest.di

import android.content.Context
import com.salesforce.remotetest.data.source.local.MoviesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ContextModule::class])
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDatabase(context: Context) = MoviesDatabase.getInstance(context).movieDao()
}