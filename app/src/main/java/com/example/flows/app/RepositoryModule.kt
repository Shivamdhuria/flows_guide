package com.example.flows.app

import com.example.flows.dogList.DogListFragmentRepository
import com.example.flows.dogList.local.DogDao
import com.example.flows.dogList.network.MainActivityApi
import com.example.flows.dogList.network.RemoteDataSource
import com.example.flows.favorite.FavoriteDogFragmentRepository
import com.example.flows.favorite.local.FavoriteDao
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
    fun provideDogListRepository(dogDao: DogDao, dogRDS: RemoteDataSource, api: MainActivityApi):
            DogListFragmentRepository = DogListFragmentRepository(dogDao, dogRDS, api)

    @Provides
    @ActivityRetainedScoped
    fun provideFavoriteDogRepository(favoriteDao: FavoriteDao): FavoriteDogFragmentRepository = FavoriteDogFragmentRepository(favoriteDao)
}
