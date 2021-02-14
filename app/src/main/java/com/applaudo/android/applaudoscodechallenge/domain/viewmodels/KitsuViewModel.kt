package com.applaudo.android.applaudoscodechallenge.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.repositories.ApplaudoRepository
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.ArticleData
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.ANIME_DATA_TYPE

class KitsuViewModel(application: Application) : AndroidViewModel(application) {

    private val applaudoRepository: ApplaudoRepository

    init {
        applaudoRepository = ApplaudoRepository(application)
    }

    fun getAnime(dataType: ANIME_DATA_TYPE, category:String) : MutableLiveData<ArticleData>{
        return applaudoRepository.getAnime(dataType, category)
    }

}