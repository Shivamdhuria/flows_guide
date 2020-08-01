package com.example.flows.util

// abstract class ApiResponseHandler<Data>(private val response: GenericApiResponse<Data?>) {
//
//    private val TAG: String = "AppDebug"
//
//    suspend fun getResult(): GenericApiResponse<Any?> {
//
//        return when (response) {
//
//            is GenericApiResponse.GenericErrorType, is GenericApiResponse.NetworkErrorType -> response
//
//            is GenericApiResponse.Success -> handleSuccessResponse(response.value)
//
//        }
//    }
//
//    abstract suspend fun handleSuccessResponse(resultObj: Data?): GenericApiResponse<Any>
//
// }

// return when (response) {
//
//            is GenericApiResponse.GenericErrorType -> { }
//
//            is GenericApiResponse.NetworkErrorType -> { }
//
//            is GenericApiResponse.Success -> {
//                when (response.value) {
//                    null -> {
//
//                    }
//                    else -> {
//                        handleSuccess(resultObj = response.value)
//                    }
//                }
//            }
//
//        }