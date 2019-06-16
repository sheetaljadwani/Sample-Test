package com.salesforce.remotetest.di

import com.salesforce.remotetest.base.BaseActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RepositoryModule::class, RxJavaModule::class])
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
}