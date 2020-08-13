package com.example.flows.dogList.network

import com.example.flows.dogList.data.ApiResponse
import retrofit2.http.GET

interface MainActivityApi {

    @GET("breeds/image/random")
    suspend fun getRandomImageByUrl(): ApiResponse<String>
}