package com.example.flows.app

import com.example.flows.main.MainActivityRepository
import com.example.flows.main.local.DogDao
import com.example.flows.main.network.MainActivityApi
import com.example.flows.main.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMainActivityRepository(dogDao: DogDao, dogRDS: RemoteDataSource, api: MainActivityApi):
            MainActivityRepository = MainActivityRepository(dogDao, dogRDS, api)
}
