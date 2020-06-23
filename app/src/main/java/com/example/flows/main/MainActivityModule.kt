package com.example.flows.main

import com.example.flows.main.local.DogDao
import com.example.flows.main.network.MainActivityApi
import com.example.flows.main.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
internal class MainActivityModule {

    @Provides
    fun provideMainActivityRepository(dogDao: DogDao, dogRDS: RemoteDataSource, api: MainActivityApi): MainActivityRepository =
        MainActivityRepository(dogDao,dogRDS,api)

    @Provides
    fun provideEditProfileApi(retrofit: Retrofit): MainActivityApi = retrofit.create(MainActivityApi::class.java)

    @Provides
    fun provideRemoteDataSource(api: MainActivityApi): RemoteDataSource = RemoteDataSource(api)
}