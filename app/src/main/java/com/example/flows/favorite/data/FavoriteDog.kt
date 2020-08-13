package com.example.flows.favorite.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_dog_table")
data class FavoriteDog(
    val breed: String,
    val imageUrl: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)