package com.example.flows.favorite

import com.example.flows.favorite.local.FavoriteDao
import javax.inject.Inject

class FavoriteDogFragmentRepository @Inject constructor(private val favoriteDao: FavoriteDao) {

    internal val favoriteDogsFlow = favoriteDao.loadFavoriteDogsFlow()
}