package com.sm.api.base

import com.sm.domain.base.JsonSerializer
import com.sm.domain.base.fromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerErrorWrapper(
    @Json(name = "error")
    val error: ServerErrorBody?
)

@JsonClass(generateAdapter = true)
data class ServerErrorBody(
    @Json(name = "code")
    val code: Int,
    @Json(name = "message")
    val message: String?
)

internal fun String?.parseResponseBodyError(serializer: JsonSerializer): String? {
    return if (this.isNullOrBlank()) null
    else {
        val errorBody = serializer.fromJson<ServerErrorWrapper>(this)
        errorBody?.error?.message
    }
}