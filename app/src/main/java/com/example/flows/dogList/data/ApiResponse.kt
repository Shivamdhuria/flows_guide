package com.example.flows.dogList.data

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("status") val status: String,
    @SerializedName("message") val message: T
)