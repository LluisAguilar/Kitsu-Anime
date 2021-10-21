package com.applaudo.android.applaudoscodechallenge.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.db.entities.ArticlesFavoriteEntity
import com.applaudo.android.applaudoscodechallenge.data.repositories.ApplaudoRepositoryImpl
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.chapters_characters.ChaptersCharacters
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.genres.Genres
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.MangaArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.StreamerData
import com.applaudo.android.applaudoscodechallenge.domain.usecases.GetAnimeArticleUseCase
import com.applaudo.android.applaudoscodechallenge.domain.usecases.GetMangaArticleUseCase
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings.Companion.ANIME_DATA_TYPE

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplaudoRepositoryImpl: ApplaudoRepositoryImpl
    private val mGetAnimeArticleUseCase : GetAnimeArticleUseCase
    private val mGetMangaArticleUseCase : GetMangaArticleUseCase

    init {
        mApplaudoRepositoryImpl = ApplaudoRepositoryImpl(application)
        mGetAnimeArticleUseCase = GetAnimeArticleUseCase(application)
        mGetMangaArticleUseCase = GetMangaArticleUseCase(application)
    }

    fun getAnime(dataType: ANIME_DATA_TYPE, category:String, searchText:String, articleId:String, streamer:String) : MutableLiveData<AnimeArticleData>{
        return mGetAnimeArticleUseCase.getAnime(dataType, category, searchText, articleId, streamer)
    }

    fun getEpisodesCharacters(dataType: UtilStrings.Companion.ARTICLE_DATA_TYPE, articleId: String) : MutableLiveData<ChaptersCharacters> {
        return mApplaudoRepositoryImpl.getEpisodesCharacters(dataType, articleId)
    }

    fun getGenres(dataType: UtilStrings.Companion.ARTICLE_GENRE_TYPE, articleId: String): MutableLiveData<Genres> {
        return mApplaudoRepositoryImpl.getGenres(dataType, articleId)
    }

    fun getStreamersImage(): MutableLiveData<ArrayList<StreamerData>> {
        return  mApplaudoRepositoryImpl.getStreamersImage()
    }

    fun getManga(dataType: UtilStrings.Companion.MANGA_DATA_TYPE, category:String, searchText:String, articleId:String):MutableLiveData<MangaArticleData>{
        return mGetMangaArticleUseCase.getManga(dataType, category, searchText, articleId)
    }

    fun insertFavoriteArticle(articleData: ArticleData){
        mApplaudoRepositoryImpl.insertFavoriteArticle(ArticlesFavoriteEntity(articleData.title!!,articleData.imageUrl!!,articleData.articleType,articleData.id.toInt()))
    }

    fun deleteFavoriteArticle(id:Int){
        mApplaudoRepositoryImpl.deleteFavoriteArticle(id)
    }

    fun getFavorites(): LiveData<List<ArticlesFavoriteEntity>> {
        return mApplaudoRepositoryImpl.getFavorites()
    }

}