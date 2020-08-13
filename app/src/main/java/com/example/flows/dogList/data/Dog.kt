package com.example.flows.dogList.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dog(
    val breed: String,
    val imageUrl: String?,
    var isTopDog: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)