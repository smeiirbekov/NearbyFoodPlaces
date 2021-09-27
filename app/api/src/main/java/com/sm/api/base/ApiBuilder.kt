package com.sm.api.base

import com.sm.domain.base.JsonSerializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class ApiBuilder(
    val serializer: JsonSerializer
) {

    val loggingInterceptor: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return httpLoggingInterceptor
        }

    inline fun <reified T> buildService(baseUrl: String, vararg interceptors: Interceptor): T {
        val okHttp = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
        interceptors.forEach { okHttp.addInterceptor(it) }
        val builder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(serializer.getConverterFactory())
            .addCallAdapterFactory(CallAdapterFactory(serializer))
            .client(okHttp.build())
        val retrofit = builder.build()
        return retrofit.create(T::class.java)
    }

}