package com.alejandrorios.meli_challenge.data.utils

import retrofit2.Response
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

inline fun <param, result> Response<param>.handleResponse(
    continuation: Continuation<CallResponse<result>>,
    transformation: (predicate: param) -> result
) {
    with(continuation) {
        if (!isSuccessful || errorBody() != null) {
            resume(CallResponse.failure<NetworkErrorException>(NetworkErrorException(errorBody()?.string() ?: "Response body is null")))
            return
        }

        try {
            resume(CallResponse.success(transformation(body()!!)))
        } catch (e: Exception) {
            resume(CallResponse.failure<Throwable>(Throwable(e.localizedMessage ?: "Unknown error")))
        }
    }
}
