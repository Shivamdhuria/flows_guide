package com.example.flows.extensions

import com.example.flows.error.ErrorResponse
import com.example.flows.error.ResultWrapper
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper {
    // Although suspend modifier for retrofit is being used, withContext() is still needed for testing in future
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    ResultWrapper.GenericError(code, errorResponse)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}

// internal fun <T, L> GenericApiResponse.handleApiResponse(
//    success: (GenericApiResponse) -> L,
//    error: (GenericApiResponse) -> L
// ): L {
//    return when (this) {
//        is Success<*> -> success(this)
//        else -> error(this)
//    }
// }
//
suspend fun handleException(throwable: Throwable): ResultWrapper {
    return when (throwable) {
        is IOException -> ResultWrapper.NetworkError
        is HttpException -> {
            val code = throwable.code()
            val errorResponse = convertErrorBody(throwable)
            ResultWrapper.GenericError(code, errorResponse)
        }
        else -> {
            ResultWrapper.GenericError(null, null)
        }
    }
}