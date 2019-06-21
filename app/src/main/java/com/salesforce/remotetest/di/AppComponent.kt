package com.salesforce.remotetest.di

import com.salesforce.remotetest.base.BaseActivity
import com.salesforce.remotetest.data.source.MoviesRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RepositoryModule::class, RxJavaModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)

    fun getMoviesRepository() : MoviesRepository
}