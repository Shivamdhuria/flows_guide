package com.elixer.paws.dogList

import com.elixer.paws.dogList.data.ApiResponse
import com.elixer.paws.dogList.data.Dog
import com.elixer.paws.dogList.local.DogDao
import com.elixer.paws.dogList.network.MainActivityApi
import com.elixer.paws.dogList.network.RemoteDataSource
import com.elixer.paws.error.ResultWrapper
import com.elixer.paws.extensions.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DogListFragmentRepository @Inject constructor(
    private val dogDao: DogDao,
    private val dogsRDS: RemoteDataSource,
    private val api: MainActivityApi
) {

    @ExperimentalCoroutinesApi
    fun getSearchedDogs(search: String): Flow<List<Dog>> {
        return dogDao.getSearchedDogs(search) // Get searched dogs from Room Database
                // Combine the result with another flow
//                .combine(topBreedsFlow) { dogs, topDogs ->
//                    dogs.applyToDog(topDogs)
//                }
                .flowOn(Dispatchers.Default)
                // Return the latest values
                .conflate()
    }

    private val topBreedsFlow = dogsRDS.favoritesSortOrder()

    suspend fun tryFetchAndUpdate(): ResultWrapper {

        val api = safeApiCall(Dispatchers.IO) { api.getRandomImageByUrl() }
        when (api) {
            is ResultWrapper.Success<*> -> {
                val dogResponse = api.value as ApiResponse<String>
                val breedImageUrl = dogResponse.message
                val dog = extractBreedName(breedImageUrl)?.let { Dog(it, breedImageUrl, false, null) }
                dog?.run {
                    dogDao.save(this)
                }
            }
        }
        return api
    }

    private fun extractBreedName(message: String): String? {
        val breedName = message.substringAfter("breeds/").substringBefore("/")
        return breedName.replace(Regex("-"), " ").capitalize()
    }

    private fun List<Dog>.applyToDog(favoritesSortOrder: List<String>): List<Dog> {
        return this.map {
            val isTopDog = favoritesSortOrder.contains(it.breed.capitalize())
            Dog(it.breed, it.imageUrl, isTopDog, null)
        }
    }

    suspend fun clearCacheData() {
        try {
            dogDao.deleteCache()
        } catch (error: Throwable) {
        }
    }
}