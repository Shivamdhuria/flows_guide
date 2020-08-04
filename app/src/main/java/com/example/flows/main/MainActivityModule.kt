package com.example.flows.main

import com.example.flows.main.local.DogDao
import com.example.flows.main.network.MainActivityApi
import com.example.flows.main.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal class MainActivityModule {

    @Provides
    @Singleton
    fun provideMainActivityRepository(dogDao: DogDao, dogRDS: RemoteDataSource, api: MainActivityApi):
            MainActivityRepository = MainActivityRepository(dogDao, dogRDS, api)

    @Provides
    @Singleton
    fun provideEditProfileApi(retrofit: Retrofit): MainActivityApi = retrofit.create(MainActivityApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: MainActivityApi): RemoteDataSource = RemoteDataSource(api)

    @Provides
    fun printRandomInteger(): String = (0..10).random().toString()
}
