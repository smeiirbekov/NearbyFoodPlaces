package com.sm.domain.base

import retrofit2.Converter

interface JsonSerializer {

    fun<T> jsonToObject(type: Class<T>, string: String?): T?

    fun<T> objectToJson(type: Class<T>, item: T?): String?

    fun getConverterFactory(): Converter.Factory

}

inline fun<reified T> JsonSerializer.fromJson(string: String?) = jsonToObject(T::class.java, string)

inline fun<reified T> JsonSerializer.toJson(item: T?) = objectToJson(T::class.java, item)