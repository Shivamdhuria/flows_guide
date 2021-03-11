package com.elixer.paws.error

data class ErrorResponse(
    val code: Int,
    val message: String,
    val data: Any
)