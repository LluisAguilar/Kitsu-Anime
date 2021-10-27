package com.applaudo.android.applaudoscodechallenge.data.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.db.entities.ArticlesFavoriteEntity
import com.applaudo.android.applaudoscodechallenge.data.repositories.datasource.ApplaudoLocalDataSource
import com.applaudo.android.applaudoscodechallenge.data.repositories.datasource.ApplaudoRemoteDataSource
import com.applaudo.android.applaudoscodechallenge.data.repositories.helpers.AnimeArticleDataToAnimeArticleDataUseCase
import com.applaudo.android.applaudoscodechallenge.data.repositories.helpers.animeDataDomainToAnimeData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.chapters_characters.ChaptersCharacters
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.genres.Genres
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.MangaArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.StreamerData
import com.applaudo.android.applaudoscodechallenge.domain.models.anime.AnimeArticleDataUseCase
import com.applaudo.android.applaudoscodechallenge.domain.utils.UtilStringsDomain
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings.Companion.ANIME_DATA_TYPE
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings.Companion.MANGA_DATA_TYPE
import retrofit2.Response
import java.util.ArrayList

class ApplaudoRepositoryImpl(application: Application) : ApplaudoRepository {

    private var mApplaudoRemoteDataSource:ApplaudoRemoteDataSource
    private var mApplaudoLocalDataSource:ApplaudoLocalDataSource

    init {
        mApplaudoRemoteDataSource = ApplaudoRemoteDataSource()
        mApplaudoLocalDataSource = ApplaudoLocalDataSource(application)
    }

    override fun getAnime(dataType: ANIME_DATA_TYPE, category: String, searchText: String, articleId: String, streamer: String):MutableLiveData<AnimeArticleData>{
        return mApplaudoRemoteDataSource.getAnimeData(dataType,category,searchText,articleId,streamer)
    }

    override fun getManga(dataType: MANGA_DATA_TYPE, category: String, searchText: String, articleId: String):MutableLiveData<MangaArticleData>{
        return mApplaudoRemoteDataSource.getMangaData(dataType,category,searchText,articleId)
    }

    override fun getStreamersImage(): MutableLiveData<ArrayList<StreamerData>> {
        return mApplaudoRemoteDataSource.getStreamerImage()
    }

    override fun insertFavoriteArticle(articlesFavoriteEntity: ArticlesFavoriteEntity) {
        mApplaudoLocalDataSource.insertFavorite(articlesFavoriteEntity)
    }

    override fun deleteFavoriteArticle(id: Int) {
        mApplaudoLocalDataSource.deleteFavoriteById(id)
    }

    override fun getFavorites():LiveData<List<ArticlesFavoriteEntity>> {
        return mApplaudoLocalDataSource.getFavorites()
    }

    override fun getEpisodesCharacters(
        dataType: UtilStrings.Companion.ARTICLE_DATA_TYPE, articleId: String): MutableLiveData<ChaptersCharacters> {
        return mApplaudoRemoteDataSource.getEpisodesCharacters(dataType, articleId)
    }

    override fun getGenres(dataType: UtilStrings.Companion.ARTICLE_GENRE_TYPE, articleId: String): MutableLiveData<Genres> {
        return mApplaudoRemoteDataSource.getGenres(dataType, articleId)
    }

    override suspend fun getAnimeCoroutines(
        domainDataType: UtilStringsDomain.Companion.ANIME_DATA_TYPE,
        category: String,
        searchText: String,
        articleId: String,
        streamer: String
    ): Response<AnimeArticleDataUseCase> {
        return AnimeArticleDataToAnimeArticleDataUseCase(mApplaudoRemoteDataSource.getAnimes(animeDataDomainToAnimeData(domainDataType), category, searchText, articleId, streamer))
    }


}