package com.blumonpay.interjet.data.retrofit


import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class InterjetClient {

    private val interjetService: InterjetService
    private val retrofit: Retrofit

    // Singleton del cliente
    companion object {
        var instance: InterjetClient? = null
            get () {
                if (field == null)
                    instance =
                        InterjetClient()

                return field
            }
    }

    init {
        // Incluir el interceptor para las peticiones
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)

        val client = okHttpClientBuilder.build()

        // Construir cliente retrofit
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        // Instancioamos servicio de retrofit a partir del objeto retrofit
        interjetService = retrofit.create(InterjetService::class.java)
    }

    // Para obtener la referencia a este servicio Service
    fun getService() = interjetService

}