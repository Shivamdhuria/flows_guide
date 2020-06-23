package com.example.flows.main.network

import com.example.flows.main.data.ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MainActivityApi {

    @GET("breeds/list/all")
    suspend fun getAllBreeds(): ApiResponse<Map<String, List<String>>>

    @GET("breeds/list/all")
    fun getBreedsListAsync(): Call<ApiResponse<Map<String, List<String>>>>

    @GET("breed/{breedName}/images/random")
    fun getImageByUrlAsync(@Path("breedName") breedName: String): Call<ApiResponse<String>>

    @GET("breed/{breedName}/images/random")
    suspend fun getImageByUrl(@Path("breedName") breedName: String): ApiResponse<String>

    @GET("breeds/list/all")
    suspend fun getBreedsList(): ApiResponse<Map<String, List<String>>>

    @GET("breeds/image/random")
    suspend fun getRandomImageByUrl(): ApiResponse<String>

}