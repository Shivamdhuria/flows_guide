package com.example.flows.app

import android.content.Context
import androidx.room.Room
import com.example.flows.BuildConfig
import com.example.flows.app.AppDatabase.Companion.DATABASE_NAME
import com.example.flows.main.local.DogDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
internal class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: CoroutineApplication): Context = application.applicationContext

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

//    @Provides
// //    @Singleton
// //    fun provideOkHttpClient(): OkHttpClient {
// //        val httpClient = OkHttpClient.Builder()
// //        httpClient.readTimeout(1, TimeUnit.MINUTES)
// //        httpClient.connectTimeout(1, TimeUnit.MINUTES)
// //        httpClient.writeTimeout(1, TimeUnit.MINUTES)
// //        httpClient.addInterceptor(loggingInterceptor())
// //        return httpClient.build()
// //    }
// //
// //    @Provides
// //    @Singleton
// //    private fun loggingInterceptor(): HttpLoggingInterceptor {
// //        val interceptor = HttpLoggingInterceptor()
// //        if (BuildConfig.DEBUG) {
// //            interceptor.level = HttpLoggingInterceptor.Level.BODY
// //        } else {
// //            interceptor.level = HttpLoggingInterceptor.Level.NONE
// //        }
// //        return interceptor
// //    }

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
    } else OkHttpClient
            .Builder()
            .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDatabase = Room
        .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDogDao(db: AppDatabase): DogDao = db.getDogDao()
}