package com.example.flows.main.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Dog(
    @PrimaryKey
    val breed : String,
    val imageUrl: String?,
    var isTopDog: Boolean = false
)