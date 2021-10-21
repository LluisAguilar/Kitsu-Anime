package com.applaudo.android.applaudoscodechallenge.domain.usecases

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.repositories.ApplaudoRepositoryImpl
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.MangaArticleData
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings

class GetMangaArticleUseCase(application: Application) {

    private val mApplaudoRepositoryImpl = ApplaudoRepositoryImpl(application)

    fun getManga(dataType: UtilStrings.Companion.MANGA_DATA_TYPE, category:String, searchText:String, articleId:String): MutableLiveData<MangaArticleData> {
        return mApplaudoRepositoryImpl.getManga(dataType,category,searchText,articleId)
    }

}