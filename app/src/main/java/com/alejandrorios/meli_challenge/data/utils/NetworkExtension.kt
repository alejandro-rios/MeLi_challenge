package com.alejandrorios.meli_challenge.data.utils

import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

inline fun <param, result> retrofit2.Response<param>.handleResponse(
    continuation: Continuation<CallResponse<result>>,
    transformation: (predicate: param) -> result
) {
    with(continuation) {
        if (!isSuccessful || errorBody() != null) {
            resume(CallResponse.failure<NetworkErrorException>(NetworkErrorException(errorBody()?.string())))
            return
        }

        try {
            resume(CallResponse.success(transformation(body()!!)))
        } catch (t: Throwable) {
            resume(CallResponse.failure<Throwable>(t))
        }
    }
}
