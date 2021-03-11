package com.elixer.paws.error

sealed class ResultWrapper {
    data class Success<T>(val value: T) : ResultWrapper()
    data class GenericError(val code: Int? = null, val error: ErrorResponse? = null) : ResultWrapper()
    object NetworkError : ResultWrapper()
    data class Loading(val isLoading: Boolean = true) : ResultWrapper()
}