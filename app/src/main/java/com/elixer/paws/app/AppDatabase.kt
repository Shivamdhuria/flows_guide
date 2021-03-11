package com.elixer.paws.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.elixer.paws.dogList.data.Dog
import com.elixer.paws.dogList.local.DogDao
import com.elixer.paws.favorite.data.FavoriteDog
import com.elixer.paws.favorite.local.FavoriteDao

@Database(entities = [Dog::class, FavoriteDog::class], version = 7)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME: String = "app_db"
    }

    abstract fun getDogDao(): DogDao
    abstract fun getFavoriteDogDao(): FavoriteDao
}