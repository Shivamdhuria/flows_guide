package com.example.flows.main.network

import com.example.flows.main.data.ApiResponse
import retrofit2.http.GET

interface MainActivityApi {

    @GET("breeds/image/random")
    suspend fun getRandomImageByUrl(): ApiResponse<String>
}