package com.elixer.paws.app

import com.elixer.paws.dogList.DogListFragmentRepository
import com.elixer.paws.dogList.local.DogDao
import com.elixer.paws.dogList.network.MainActivityApi
import com.elixer.paws.dogList.network.RemoteDataSource
import com.elixer.paws.favorite.FavoriteDogFragmentRepository
import com.elixer.paws.favorite.local.FavoriteDao
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
