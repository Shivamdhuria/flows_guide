package com.example.flows.app

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
internal abstract class AppBuilderModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: CustomViewModelFactory): ViewModelProvider.Factory
}