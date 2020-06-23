package com.example.flows.main.local


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flows.main.data.Dog
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(dog: Dog)

//  @Insert(onConflict = OnConflictStrategy.REPLACE)
//  suspend fun saveAll(episodes: List<Episode>)

    @Query("SELECT * FROM dog")
    fun loadAllEpisodesFlow(): Flow<List<Dog>>

    @Query("DELETE FROM DOG ")
    suspend fun deleteCache()

//  @Query("SELECT * FROM episode WHERE trilogy = :trilogyNumber ORDER BY number")
//  fun getEpisodesForTrilogyNumberFlow(trilogyNumber: Int ): Flow<List<Episode>>
}