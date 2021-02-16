package com.applaudo.android.applaudoscodechallenge.domain.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.db.entities.ArticlesFavoriteEntity
import com.applaudo.android.applaudoscodechallenge.data.repositories.ApplaudoRepository
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.MangaArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.FavoriteArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.StreamerData
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.ANIME_DATA_TYPE
import java.lang.NullPointerException

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplaudoRepository: ApplaudoRepository

    init {
        mApplaudoRepository = ApplaudoRepository(application)
    }

    fun getAnime(dataType: ANIME_DATA_TYPE, category:String, searchText:String, articleId:String, streamer:String) : MutableLiveData<AnimeArticleData>{
        return mApplaudoRepository.getAnime(dataType, category,searchText,articleId,streamer)
    }

    fun getStreamersImage(): MutableLiveData<ArrayList<StreamerData>> {
        return  mApplaudoRepository.getStreamersImage()
    }

    fun getManga(dataType: UtilStrings.Companion.MANGA_DATA_TYPE, category:String, searchText:String, articleId:String):MutableLiveData<MangaArticleData>{
        return mApplaudoRepository.getManga(dataType,category,searchText,articleId)
    }

    fun insertFavoriteArticle(articleData: ArticleData){
        mApplaudoRepository.insertFavoriteArticle(ArticlesFavoriteEntity(articleData.title!!,articleData.imageUrl!!,articleData.articleType!!,articleData.id.toInt()))
    }

    fun deleteFavoriteArticle(id:Int){
        mApplaudoRepository.deleteFavoriteArticle(id)
    }

    fun getFavorites(): LiveData<List<ArticlesFavoriteEntity>> {
        return mApplaudoRepository.getFavorites()
    }

}