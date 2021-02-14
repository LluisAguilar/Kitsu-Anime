package com.blumonpay.interjet.data.retrofit


import com.applaudo.android.applaudoscodechallenge.data.retrofit.AuthInterceptor
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class KitsuClient {

    private val kitsuService: KitsuService
    private val retrofit: Retrofit

    companion object {
        var instance: KitsuClient? = null
            get () {
                if (field == null)
                    instance =
                        KitsuClient()

                return field
            }
    }

    init {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder
                .addInterceptor(AuthInterceptor())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        val client = okHttpClientBuilder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        kitsuService = retrofit.create(KitsuService::class.java)
    }

    fun getService() = kitsuService

}