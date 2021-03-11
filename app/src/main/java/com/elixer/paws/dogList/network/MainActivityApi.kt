package com.elixer.paws.dogList.network

import com.elixer.paws.dogList.data.ApiResponse
import retrofit2.http.GET

interface MainActivityApi {

    @GET("breeds/image/random")
    suspend fun getRandomImageByUrl(): ApiResponse<String>
}