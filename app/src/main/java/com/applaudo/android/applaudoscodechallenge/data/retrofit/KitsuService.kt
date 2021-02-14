package com.blumonpay.interjet.data.retrofit

import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.ArticleData
import retrofit2.Call
import retrofit2.http.*

interface KitsuService {

    @GET("trending/anime?")
    fun getTrendingAnime(): Call<ArticleData>

    @GET("anime?filter[status]=current")
    fun getAnimeOnAir(): Call<ArticleData>

    @GET
    fun getAnimeByCategory(@Url url:String): Call<ArticleData>


}