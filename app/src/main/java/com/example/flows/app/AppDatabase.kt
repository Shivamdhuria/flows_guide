package com.example.flows.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flows.dogList.data.Dog
import com.example.flows.dogList.local.DogDao
import com.example.flows.favorite.data.FavoriteDog
import com.example.flows.favorite.local.FavoriteDao

@Database(entities = [Dog::class, FavoriteDog::class], version = 7)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME: String = "app_db"
    }

    abstract fun getDogDao(): DogDao
    abstract fun getFavoriteDogDao(): FavoriteDao
}