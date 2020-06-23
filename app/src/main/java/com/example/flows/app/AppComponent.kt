package com.example.flows.app

import com.example.flows.main.MainActivityBuilderModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class,
        AppBuilderModule::class,
        MainActivityBuilderModule::class
    ]
)
internal interface AppComponent : AndroidInjector<CoroutineApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<CoroutineApplication>
}