package com.salesforce.remotetest.di

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(private  val context: Context) {

    @Singleton
    @Provides
    fun context(): Context {
        return context.getApplicationContext()
    }
}