package com.salesforce.remotetest.di

import com.salesforce.remotetest.base.BaseTest
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetModule::class, RepositoryModule::class, TestRxJavaModule::class])
interface TestAppComponent {
    fun inject(baseTest: BaseTest)
}