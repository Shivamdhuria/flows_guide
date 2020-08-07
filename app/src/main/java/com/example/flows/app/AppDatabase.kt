package com.example.flows.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flows.main.data.Dog
import com.example.flows.main.local.DogDao

@Database(entities = [Dog::class], version = 6)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME: String = "app_db"
    }

    abstract fun getDogDao(): DogDao
}