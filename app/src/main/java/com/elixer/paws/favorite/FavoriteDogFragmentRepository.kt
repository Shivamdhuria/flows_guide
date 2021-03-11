package com.elixer.paws.favorite

import com.elixer.paws.favorite.local.FavoriteDao
import javax.inject.Inject

class FavoriteDogFragmentRepository @Inject constructor(private val favoriteDao: FavoriteDao) {

    internal val favoriteDogsFlow = favoriteDao.loadFavoriteDogsFlow()
}