package com.applaudo.android.applaudoscodechallenge.domain.usecases

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.repositories.ApplaudoRepositoryImpl
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.anime.AnimeArticleDataUseCase
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings
import retrofit2.Response

class GetAnimeArticleCleanUseCase(application: Application) {

    private val mApplaudoRepositoryImpl = ApplaudoRepositoryImpl(application)


    suspend fun getAnimeCoroutines(dataType: UtilStrings.Companion.ANIME_DATA_TYPE, category:String, searchText:String, articleId:String, streamer:String) : Response<AnimeArticleDataUseCase> {
        return mApplaudoRepositoryImpl.getAnimeCoroutines(dataType, category,searchText,articleId,streamer)
    }

}