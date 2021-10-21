package com.applaudo.android.applaudoscodechallenge.domain.usecases

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.repositories.ApplaudoRepositoryImpl
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings

class GetAnimeArticleUseCase(application: Application) {

    private val mApplaudoRepositoryImpl = ApplaudoRepositoryImpl(application)


    fun getAnime(dataType: UtilStrings.Companion.ANIME_DATA_TYPE, category:String, searchText:String, articleId:String, streamer:String) : MutableLiveData<AnimeArticleData> {
        return mApplaudoRepositoryImpl.getAnime(dataType, category,searchText,articleId,streamer)
    }

}