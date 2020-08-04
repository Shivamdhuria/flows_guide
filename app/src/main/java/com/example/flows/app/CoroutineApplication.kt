package com.example.flows.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CoroutineApplication : Application()

// Pre HILT using Dagger

// internal class CoroutineApplication : DaggerApplication() {
//
//     override fun onCreate() {
//         super.onCreate()
//     }
//
//     override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
//         return DaggerAppComponent.factory().create(this)
//     }
// }