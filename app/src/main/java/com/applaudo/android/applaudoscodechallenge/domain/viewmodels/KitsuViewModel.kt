package com.applaudo.android.applaudoscodechallenge.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.repositories.ApplaudoRepository
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.MangaArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.StreamerData
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.ANIME_DATA_TYPE

class KitsuViewModel(application: Application) : AndroidViewModel(application) {

    private val applaudoRepository: ApplaudoRepository

    init {
        applaudoRepository = ApplaudoRepository(application)
    }

    fun getAnime(dataType: ANIME_DATA_TYPE, category:String, searchText:String, articleId:String, streamer:String) : MutableLiveData<AnimeArticleData>{
        return applaudoRepository.getAnime(dataType, category,searchText,articleId,streamer)
    }

    fun getStreamersImage(): MutableLiveData<ArrayList<StreamerData>> {
        return  applaudoRepository.getStreamersImage()
    }

    fun getManga(dataType: UtilStrings.Companion.MANGA_DATA_TYPE, category:String, searchText:String, articleId:String):MutableLiveData<MangaArticleData>{
        return applaudoRepository.getManga(dataType,category,searchText,articleId)
    }

}