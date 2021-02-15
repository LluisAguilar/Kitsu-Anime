package com.blumonpay.interjet.data.retrofit

import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.MangaArticleData
import retrofit2.Call
import retrofit2.http.*

interface KitsuService {

    @GET("trending/anime?")
    fun getTrendingAnime(): Call<AnimeArticleData>

    @GET("anime?filter[status]=current")
    fun getAnimeOnAir(): Call<AnimeArticleData>

    @GET
    fun getAnimeByCategory(@Url url:String): Call<AnimeArticleData>

    @GET("trending/manga?")
    fun getTrendingManga(): Call<MangaArticleData>

    @GET("manga?filter[status]=current")
    fun getMangaOnAir(): Call<MangaArticleData>

    @GET("manga?filter[status]=finished")
    fun getMangaFinished(): Call<MangaArticleData>

    @GET
    fun getMangaByCategory(@Url url:String): Call<MangaArticleData>

    @GET
    fun getAnimeByTextSearch(@Url url:String): Call<AnimeArticleData>

    @GET
    fun getAnimeByCategoryTextSearch(@Url url:String): Call<AnimeArticleData>

    @GET
    fun getMangaByTextSearch(@Url url:String): Call<MangaArticleData>

    @GET
    fun getMangaByCategoryTextSearch(@Url url:String): Call<MangaArticleData>

    @GET
    fun getAnimeById(@Url url:String): Call<AnimeArticleData>

    @GET
    fun getAnimeByStreamer(@Url url:String): Call<AnimeArticleData>

    @GET
    fun getMAngaById(@Url url:String): Call<MangaArticleData>
}