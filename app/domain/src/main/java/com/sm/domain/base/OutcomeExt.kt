package com.sm.domain.base

fun<T, R> Outcome<T>.map(
    transform: (T?) -> R? = { null }
): Outcome<R> {
    return when(this) {
        is Outcome.Success -> Outcome.Success(transform(data), code)
        is Outcome.Error.ResponseError -> Outcome.Error.ResponseError(message, params)
        Outcome.Error.ConnectionError -> Outcome.Error.ConnectionError
        is Outcome.Error.UnknownError -> Outcome.Error.UnknownError(message)
    }
}

fun<T> Outcome<T>.onSuccess(
    action: (data: T?) -> Unit
): Outcome<T> {
    if (this is Outcome.Success) action.invoke(this.data)
    return this
}

suspend fun<T> Outcome<T>.doOnSuccess(
    action: suspend (data: T?) -> Unit
): Outcome<T> {
    if (this is Outcome.Success) action.invoke(this.data)
    return this
}

fun<T> Outcome<T>.onError(
    action: (error: Outcome.Error) -> Unit
): Outcome<T> {
    if (this is Outcome.Error) action.invoke(this)
    return this
}

fun<T> Outcome<T>.onResponseError(
    action: (error: Outcome.Error.ResponseError, code: Int?) -> Unit
): Outcome<T> {
    if (this is Outcome.Error.ResponseError) action.invoke(this, params?.code)
    return this
}

fun<T> Outcome<T>.onConnectionError(
    action: () -> Unit
): Outcome<T> {
    if (this is Outcome.Error.ConnectionError) action.invoke()
    return this
}

fun<T> Outcome<T>.onUnknownError(
    action: () -> Unit
): Outcome<T> {
    if (this is Outcome.Error.UnknownError) action.invoke()
    return this
}