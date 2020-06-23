package com.example.flows.main

import androidx.lifecycle.ViewModel
import com.example.flows.app.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [MainActivityModule::class])
internal abstract class MainActivityBuilderModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindsViewModel(viewModel: MainActivityViewModel): ViewModel
}