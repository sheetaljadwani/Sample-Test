package com.salesforce.remotetest.base

import android.app.Application
import com.salesforce.remotetest.data.source.MoviesRepository
import com.salesforce.remotetest.di.*
import com.salesforce.remotetest.util.BASE_URL

open class BaseApplication : Application() {

    lateinit var moviesRepository: MoviesRepository
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        this.appComponent = this.initDagger()
        this.moviesRepository = appComponent.getMoviesRepository()
    }

    protected open fun initDagger(): AppComponent
            = DaggerAppComponent.builder()
            .contextModule(ContextModule(this))
            .netModule(NetModule(BASE_URL))
            .repositoryModule(RepositoryModule())
            .rxJavaModule(RxJavaModule())
            .build()
}