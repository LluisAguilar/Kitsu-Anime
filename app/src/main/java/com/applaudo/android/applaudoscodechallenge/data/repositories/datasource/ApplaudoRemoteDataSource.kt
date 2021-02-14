package com.applaudo.android.applaudoscodechallenge.data.repositories.datasource

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.*
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings
import com.blumonpay.interjet.data.retrofit.KitsuClient
import com.blumonpay.interjet.data.retrofit.KitsuService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.ANIME_DATA_TYPE

class ApplaudoRemoteDataSource(private val mApplication: Application) {

    private val mKitsuClient: KitsuClient = KitsuClient.instance!!
    private val mKitsuService: KitsuService
    private val mTag = "RemoteDataSource"

    init {
        mKitsuService = mKitsuClient.getService()
    }


    fun getAnimeData(dataType: ANIME_DATA_TYPE,category:String): MutableLiveData<ArticleData> {
        val articleDataResponse = MutableLiveData<ArticleData>()
        var call: Call<ArticleData> = mKitsuService.getTrendingAnime()
        when (dataType) {
            ANIME_DATA_TYPE.TRENDING -> {
                call = mKitsuService.getTrendingAnime()
            }
            ANIME_DATA_TYPE.ONAIR -> {
                call = mKitsuService.getAnimeOnAir()
            }
            ANIME_DATA_TYPE.CATEGORIES -> {
                call = mKitsuService.getAnimeByCategory(UtilStrings.CATEGORIES_URL+category)
            }
        }
        call.enqueue(object : Callback<ArticleData> {
            override fun onResponse(call: Call<ArticleData>, response: Response<ArticleData>) {
                val articleData = ArticleData(response.code() == 200, response.body()?.data!!, response.body()?.errors)
                articleDataResponse.value = articleData
            }

            override fun onFailure(call: Call<ArticleData>, t: Throwable) {
                val errorsList: MutableList<Error> = mutableListOf()
                errorsList.add(Error("400", t.message.toString(), "400", t.cause.toString()))
                val articleData = ArticleData(false, listOf(), KitsuError(errorsList))
                articleDataResponse.value = articleData
            }

        })

        return articleDataResponse
    }


}