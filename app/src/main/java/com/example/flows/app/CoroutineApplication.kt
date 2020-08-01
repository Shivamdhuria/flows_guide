package com.example.flows.app

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

internal class CoroutineApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }
}