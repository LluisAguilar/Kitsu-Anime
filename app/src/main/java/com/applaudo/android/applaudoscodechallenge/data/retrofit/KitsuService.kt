package com.blumonpay.interjet.data.retrofit

import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.chapters_characters.ChaptersCharacters
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.genres.Genres
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.MangaArticleData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface KitsuService {

    @GET("trending/anime?")
    fun getTrendingAnime(): Call<AnimeArticleData>

    @GET("anime?filter[status]=current")
    fun getAnimeOnAir(): Call<AnimeArticleData>

    @GET
    fun getAnime(@Url url:String): Call<AnimeArticleData>

    @GET
    fun getArticle(@Url url:String): Call<ChaptersCharacters>

    @GET("trending/manga?")
    fun getTrendingManga(): Call<MangaArticleData>

    @GET("manga?filter[status]=current")
    fun getMangaOnAir(): Call<MangaArticleData>

    @GET("manga?filter[status]=finished")
    fun getMangaFinished(): Call<MangaArticleData>

    @GET
    fun getManga(@Url url:String): Call<MangaArticleData>

    @GET
    fun getGenres(@Url url:String): Call<Genres>

    @GET
    suspend fun getAnimes(@Url url: String): Response<AnimeArticleData>

    @GET("trending/anime?")
    suspend fun getTrendingAnimes(): Response<AnimeArticleData>

    @GET("anime?filter[status]=current")
    suspend fun getAnimeOnAirs(): Response<AnimeArticleData>
}