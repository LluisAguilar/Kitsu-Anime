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
import com.applaudo.android.applaudoscodechallenge.domain.models.StreamerData
import com.applaudo.android.applaudoscodechallenge.domain.models.anime.AnimeArticleDataUseCase
import com.applaudo.android.applaudoscodechallenge.domain.usecases.GetAnimeArticleCleanUseCase
import com.applaudo.android.applaudoscodechallenge.domain.usecases.GetAnimeArticleUseCase
import com.applaudo.android.applaudoscodechallenge.domain.usecases.GetMangaArticleUseCase
import com.applaudo.android.applaudoscodechallenge.ui.model.ArticleDataUI
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings.Companion.ANIME_DATA_TYPE
import kotlinx.coroutines.*

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val mApplaudoRepositoryImpl: ApplaudoRepositoryImpl
    private val mGetAnimeArticleUseCase : GetAnimeArticleUseCase
    private val mGetAnimeArticleCleanUseCase : GetAnimeArticleCleanUseCase
    private val mGetMangaArticleUseCase : GetMangaArticleUseCase
    private var animeJob:Job? = null

    init {
        mApplaudoRepositoryImpl = ApplaudoRepositoryImpl(application)
        mGetAnimeArticleUseCase = GetAnimeArticleUseCase(application)
        mGetMangaArticleUseCase = GetMangaArticleUseCase(application)
        mGetAnimeArticleCleanUseCase = GetAnimeArticleCleanUseCase(application)
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

    fun insertFavoriteArticle(articleData: ArticleDataUI){
        mApplaudoRepositoryImpl.insertFavoriteArticle(ArticlesFavoriteEntity(articleData.title!!,articleData.imageUrl!!,articleData.articleType,articleData.id.toInt()))
    }

    fun deleteFavoriteArticle(id:Int){
        mApplaudoRepositoryImpl.deleteFavoriteArticle(id)
    }

    fun getFavorites(): LiveData<List<ArticlesFavoriteEntity>> {
        return mApplaudoRepositoryImpl.getFavorites()
    }

    fun getAnimeCoroutines(dataType: ANIME_DATA_TYPE, category:String, searchText:String, articleId:String, streamer:String) : LiveData<AnimeArticleDataUseCase>{

        val responseData = MutableLiveData<AnimeArticleDataUseCase>()
        animeJob = CoroutineScope(Dispatchers.IO).launch {
            val response = mGetAnimeArticleCleanUseCase.getAnimeCoroutines(animeDataToAnimeDataDomain(dataType), category, searchText, articleId, streamer)
            if (response.isSuccessful){

                val articleData = AnimeArticleDataUseCase(
                    response.code() == 200,
                    response.body()?.data!!,
                    response.body()?.errors
                )

                responseData.postValue(articleData)
            } else {
                val errorsList: MutableList<com.applaudo.android.applaudoscodechallenge.domain.models.anime.Error> = mutableListOf()
                errorsList.add(com.applaudo.android.applaudoscodechallenge.domain.models.anime.Error("400", response.errorBody()?.toString(), "400", response.errorBody()?.toString()))
                val articleData = AnimeArticleDataUseCase(false, listOf(), com.applaudo.android.applaudoscodechallenge.domain.models.anime.KitsuError(errorsList))
                responseData.postValue(articleData)
            }
        }

        return responseData
    }

}