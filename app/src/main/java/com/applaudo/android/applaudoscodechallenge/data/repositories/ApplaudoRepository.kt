package com.applaudo.android.applaudoscodechallenge.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.db.entities.ArticlesFavoriteEntity
import com.applaudo.android.applaudoscodechallenge.data.repositories.datasource.ApplaudoLocalDataSource
import com.applaudo.android.applaudoscodechallenge.data.repositories.datasource.ApplaudoRemoteDataSource
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.MangaArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.StreamerData
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.ANIME_DATA_TYPE
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.MANGA_DATA_TYPE
import java.util.ArrayList

class ApplaudoRepository(application: Application) {

    private var mApplaudoRemoteDataSource:ApplaudoRemoteDataSource
    private var mApplaudoLocalDataSource:ApplaudoLocalDataSource

    init {
        mApplaudoRemoteDataSource = ApplaudoRemoteDataSource()
        mApplaudoLocalDataSource = ApplaudoLocalDataSource(application)
    }

    fun getAnime(dataType: ANIME_DATA_TYPE, category: String, searchText: String, articleId: String, streamer: String):MutableLiveData<AnimeArticleData>{
        return mApplaudoRemoteDataSource.getAnimeData(dataType,category,searchText,articleId,streamer)
    }

    fun getManga(dataType: MANGA_DATA_TYPE, category: String, searchText: String, articleId: String):MutableLiveData<MangaArticleData>{
        return mApplaudoRemoteDataSource.getMangaData(dataType,category,searchText,articleId)
    }

    fun getStreamersImage(): MutableLiveData<ArrayList<StreamerData>> {
        return mApplaudoRemoteDataSource.getStreamerImage()
    }

    fun insertFavoriteArticle(articlesFavoriteEntity: ArticlesFavoriteEntity) {
        mApplaudoLocalDataSource.insertFavorite(articlesFavoriteEntity)
    }

    fun deleteFavoriteArticle(id: Int) {
        mApplaudoLocalDataSource.deleteFavoriteById(id)
    }

    fun getFavorites():LiveData<List<ArticlesFavoriteEntity>> {
        return mApplaudoLocalDataSource.getFavorites()
    }


}