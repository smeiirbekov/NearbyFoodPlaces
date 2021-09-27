package com.sm.data.base

import com.sm.domain.base.JsonSerializer
import com.squareup.moshi.Moshi
import retrofit2.Converter
import retrofit2.converter.moshi.MoshiConverterFactory

class MoshiSerializer(private val moshi: Moshi): JsonSerializer {

    override fun <T> jsonToObject(type: Class<T>, string: String?): T? {
        if (string == null) return null
        return try {
            moshi.adapter(type).fromJson(string)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun <T> objectToJson(type: Class<T>, item: T?): String? {
        if (item == null) return null
        return moshi.adapter(type).toJson(item)
    }

    override fun getConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create(moshi)
    }

}