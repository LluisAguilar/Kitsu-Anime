package com.applaudo.android.applaudoscodechallenge.data.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.repositories.datasource.ApplaudoRemoteDataSource
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.ArticleData
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.ANIME_DATA_TYPE

class ApplaudoRepository(application: Application) {

    private var mApplaudoRemoteDataSource:ApplaudoRemoteDataSource

    init {
        mApplaudoRemoteDataSource = ApplaudoRemoteDataSource(application)
    }

    fun getAnime(dataType: ANIME_DATA_TYPE, category:String):MutableLiveData<ArticleData>{
        return mApplaudoRemoteDataSource.getAnimeData(dataType,category)
    }
}