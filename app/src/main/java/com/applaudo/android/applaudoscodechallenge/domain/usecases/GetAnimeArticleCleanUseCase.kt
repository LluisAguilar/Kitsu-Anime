package com.applaudo.android.applaudoscodechallenge.domain.usecases

import android.app.Application
import com.applaudo.android.applaudoscodechallenge.data.repositories.ApplaudoRepositoryImpl
import com.applaudo.android.applaudoscodechallenge.domain.models.anime.AnimeArticleDataUseCase
import com.applaudo.android.applaudoscodechallenge.domain.utils.UtilStringsDomain
import retrofit2.Response

class GetAnimeArticleCleanUseCase(application: Application) {

    private val mApplaudoRepositoryImpl = ApplaudoRepositoryImpl(application)


    suspend fun getAnimeCoroutines(domainType: UtilStringsDomain.Companion.ANIME_DATA_TYPE, category:String, searchText:String, articleId:String, streamer:String) : Response<AnimeArticleDataUseCase> {
        return mApplaudoRepositoryImpl.getAnimeCoroutines(domainType, category,searchText,articleId,streamer)
    }

}