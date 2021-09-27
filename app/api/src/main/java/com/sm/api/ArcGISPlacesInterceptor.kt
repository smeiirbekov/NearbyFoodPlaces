package com.sm.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ArcGISPlacesInterceptor(
    private val token: String
) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl: HttpUrl = originalRequest.url

        val url: HttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter("f", "json")
            .addQueryParameter("token", token)
            .build()

        val requestBuilder: Request.Builder = originalRequest.newBuilder()
            .url(url)

        return chain.proceed(requestBuilder.build())
    }

}