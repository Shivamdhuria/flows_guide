package com.elixer.paws.favorite.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elixer.paws.favorite.data.FavoriteDog
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteDog: FavoriteDog)

    @Query("SELECT * FROM favorite_dog_table")
    fun loadFavoriteDogsFlow(): Flow<List<FavoriteDog>>

    @Query("DELETE FROM favorite_dog_table ")
    suspend fun deleteCache()
}