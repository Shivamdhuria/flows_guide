package com.example.flows.app

import com.example.flows.BuildConfig
import com.example.flows.main.network.MainActivityApi
import com.example.flows.main.network.RemoteDataSource
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = retrofitConfiguration(client)

    private fun retrofitConfiguration(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addNetworkInterceptor(StethoInterceptor())
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor(loggingInterceptor())
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun loggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        return interceptor
    }

    @Provides
    @Singleton
    fun provideEditProfileApi(retrofit: Retrofit): MainActivityApi = retrofit.create(MainActivityApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: MainActivityApi): RemoteDataSource = RemoteDataSource(api)
}