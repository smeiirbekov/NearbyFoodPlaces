package com.sm.domain.base

sealed class Outcome<out T> {

    data class Success<out T>(val data: T? = null, val code: Int? = null): Outcome<T>()

    sealed class Error: Outcome<Nothing>() {

        data class ResponseError(val message: String? = null, val params: ResponseErrorParams? = null): Error()

        object ConnectionError : Error()

        data class UnknownError(val message: String? = null) : Error()

    }

}

data class ResponseErrorParams(
        val code: Int,
        val url: String? = null,
        val errorBody: String? = null
)