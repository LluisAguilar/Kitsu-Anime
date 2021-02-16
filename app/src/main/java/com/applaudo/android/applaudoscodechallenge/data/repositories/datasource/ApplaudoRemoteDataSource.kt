package com.applaudo.android.applaudoscodechallenge.data.repositories.datasource

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.Error
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.KitsuError
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.MangaArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.StreamerData
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.ANIME_DATA_TYPE
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.MANGA_DATA_TYPE
import com.blumonpay.interjet.data.retrofit.KitsuClient
import com.blumonpay.interjet.data.retrofit.KitsuService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApplaudoRemoteDataSource() {

    private val mKitsuClient: KitsuClient = KitsuClient.instance!!
    private val mKitsuService: KitsuService

    init {
        mKitsuService = mKitsuClient.getService()

    }


    fun getAnimeData(
        dataType: ANIME_DATA_TYPE,
        category: String,
        searchText: String,
        articleId: String,
        streamer:String
    ): MutableLiveData<AnimeArticleData> {
        val articleDataResponse = MutableLiveData<AnimeArticleData>()
        var call: Call<AnimeArticleData> = mKitsuService.getTrendingAnime()
        when (dataType) {
            ANIME_DATA_TYPE.TRENDING -> {
                call = mKitsuService.getTrendingAnime()
            }
            ANIME_DATA_TYPE.ONAIR -> {
                call = mKitsuService.getAnimeOnAir()
            }
            ANIME_DATA_TYPE.CATEGORIES -> {
                call = mKitsuService.getAnimeByCategory(UtilStrings.ANIME_CATEGORIES_URL + category)
            }
            ANIME_DATA_TYPE.SEARCH_TEXT -> {
                call = mKitsuService.getAnimeByTextSearch(UtilStrings.ANIME_TEXT_SEARCH_URL + searchText)
            }
            ANIME_DATA_TYPE.CATEGORIES_SEARCH -> {
                call = mKitsuService.getAnimeByCategoryTextSearch(UtilStrings.ANIME_CATEGORY_TEXT_SEARCH_URL + category)
            }
            ANIME_DATA_TYPE.INDIVIDUAL -> {
                call = mKitsuService.getAnimeById(UtilStrings.ANIME_BY_ID_URL + articleId)
            }
            ANIME_DATA_TYPE.STREAMER -> {
                call = mKitsuService.getAnimeByStreamer(UtilStrings.ANIME_BY_STREAMER_URL + streamer)
            }

        }
        call.enqueue(object : Callback<AnimeArticleData> {
            override fun onResponse(call: Call<AnimeArticleData>, response: Response<AnimeArticleData>) {
                val articleData = AnimeArticleData(
                    response.code() == 200,
                    response.body()?.data!!,
                    response.body()?.errors
                )
                articleDataResponse.value = articleData
            }

            override fun onFailure(call: Call<AnimeArticleData>, t: Throwable) {
                val errorsList: MutableList<Error> = mutableListOf()
                errorsList.add(Error("400", t.message.toString(), "400", t.cause.toString()))
                val articleData = AnimeArticleData(false, listOf(), KitsuError(errorsList))
                articleDataResponse.value = articleData
            }

        })

        return articleDataResponse
    }

    fun getMangaData(
        dataType: MANGA_DATA_TYPE,
        category: String,
        searchText: String,
        articleId: String
    ): MutableLiveData<MangaArticleData> {
        val articleDataResponse = MutableLiveData<MangaArticleData>()
        var call: Call<MangaArticleData> = mKitsuService.getTrendingManga()
        when (dataType) {
            MANGA_DATA_TYPE.TRENDING -> {
                call = mKitsuService.getTrendingManga()
            }
            MANGA_DATA_TYPE.ONAIR -> {
                call = mKitsuService.getMangaOnAir()
            }
            MANGA_DATA_TYPE.FINISHED -> {
                call = mKitsuService.getMangaFinished()
            }
            MANGA_DATA_TYPE.CATEGORIES -> {
                call = mKitsuService.getMangaByCategory(UtilStrings.MANGA_CATEGORIES_URL + category)
            }
            MANGA_DATA_TYPE.SEARCH_TEXT -> {
                call = mKitsuService.getMangaByTextSearch(UtilStrings.MANGA_TEXT_SEARCH_URL + searchText)
            }
            MANGA_DATA_TYPE.CATEGORIES_SEARCH -> {
                call = mKitsuService.getMangaByCategoryTextSearch(UtilStrings.MANGA_CATEGORY_TEXT_SEARCH_URL + category)
            }
            MANGA_DATA_TYPE.INDIVIDUAL -> {
                call = mKitsuService.getMAngaById(UtilStrings.MANGA_BY_ID_URL + articleId)
            }
        }
        call.enqueue(object : Callback<MangaArticleData> {
            override fun onResponse(call: Call<MangaArticleData>, response: Response<MangaArticleData>) {
                val articleData = MangaArticleData(
                    response.code() == 200,
                    response.body()?.data!!,
                    response.body()?.errors
                )
                articleDataResponse.value = articleData
            }

            override fun onFailure(call: Call<MangaArticleData>, t: Throwable) {
                val errorsList: MutableList<Error> = mutableListOf()
                errorsList.add(Error("400", t.message.toString(), "400", t.cause.toString()))
                val articleData = MangaArticleData(false, listOf(), KitsuError(errorsList))
                articleDataResponse.value = articleData
            }

        })

        return articleDataResponse
    }

    fun getStreamerImage(): MutableLiveData<ArrayList<StreamerData>> {
        val streamerLis = MutableLiveData<ArrayList<StreamerData>>()
        val streamerList = arrayListOf(
            StreamerData(1, "Netflix", "https://firebasestorage.googleapis.com/v0/b/applaudos-code-challenge.appspot.com/o/ApplaudoImages%2FStreamers%2Fnetflix.png?alt=media&token=c2e6b789-9607-4465-9dab-c2f315dd1e62"),
            StreamerData(2, "Amazon", "https://firebasestorage.googleapis.com/v0/b/applaudos-code-challenge.appspot.com/o/ApplaudoImages%2FStreamers%2Famazon.jpg?alt=media&token=8d84c41b-0beb-48dd-beb2-04fdd4127856"),
            StreamerData(3, "Crunchyroll", "https://firebasestorage.googleapis.com/v0/b/applaudos-code-challenge.appspot.com/o/ApplaudoImages%2FStreamers%2Fcrunchyroll.jpg?alt=media&token=808d7049-fd16-4313-ae36-3fa01fed66b7"),
            StreamerData(4, "Hulu", "https://firebasestorage.googleapis.com/v0/b/applaudos-code-challenge.appspot.com/o/ApplaudoImages%2FStreamers%2Fhulu.jpg?alt=media&token=b5c003f5-765a-4a84-a544-1a45d5580b34"),
            StreamerData(5, "Tubitv", "https://firebasestorage.googleapis.com/v0/b/applaudos-code-challenge.appspot.com/o/ApplaudoImages%2FStreamers%2Ftubitv.jpg?alt=media&token=22860224-071c-4db2-85e2-3959ec20bfaf"),
            StreamerData(6, "Youtube", "https://firebasestorage.googleapis.com/v0/b/applaudos-code-challenge.appspot.com/o/ApplaudoImages%2FStreamers%2Fyoutube.jpg?alt=media&token=1c00428f-af7a-4503-8249-e48f77ee4e84"),
            StreamerData(7, "Hidive", "https://firebasestorage.googleapis.com/v0/b/applaudos-code-challenge.appspot.com/o/ApplaudoImages%2FStreamers%2Fhidive.jpeg?alt=media&token=f95f7bd9-b43a-4039-ac65-5c80731e9c79"),
            StreamerData(8, "Viewster", "https://firebasestorage.googleapis.com/v0/b/applaudos-code-challenge.appspot.com/o/ApplaudoImages%2FStreamers%2Fviewster.png?alt=media&token=70af6f92-9dc8-4b1d-9f91-f3ea03fb7c0e"),
            StreamerData(9, "Funimation", "https://firebasestorage.googleapis.com/v0/b/applaudos-code-challenge.appspot.com/o/ApplaudoImages%2FStreamers%2Ffunimation.jpg?alt=media&token=bcfa3d0d-155c-4865-a884-dfe53d4c96d8")
        )
        streamerLis.value = streamerList

        return streamerLis
    }


}