package com.applaudo.android.applaudoscodechallenge.data.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val build = request.newBuilder()

        build.addHeader("Accept", "application/vnd.api+json")
        build.addHeader("Content-Type", "application/vnd.api+json")

        request = build.build()
        return chain.proceed(request)
    }
}