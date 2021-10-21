package com.applaudo.android.applaudoscodechallenge.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.applaudo.android.applaudoscodechallenge.data.db.entities.ArticlesFavoriteEntity
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.AnimeArticleData
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.chapters_characters.ChaptersCharacters
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.genres.Genres
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.MangaArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.StreamerData
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings
import java.util.ArrayList

interface ApplaudoRepository {


    fun getAnime(dataType: UtilStrings.Companion.ANIME_DATA_TYPE, category: String, searchText: String, articleId: String, streamer: String): MutableLiveData<AnimeArticleData>

    fun getManga(dataType: UtilStrings.Companion.MANGA_DATA_TYPE, category: String, searchText: String, articleId: String): MutableLiveData<MangaArticleData>

    fun getStreamersImage(): MutableLiveData<ArrayList<StreamerData>>

    fun insertFavoriteArticle(articlesFavoriteEntity: ArticlesFavoriteEntity)

    fun deleteFavoriteArticle(id: Int)

    fun getFavorites(): LiveData<List<ArticlesFavoriteEntity>>

    fun getEpisodesCharacters(dataType: UtilStrings.Companion.ARTICLE_DATA_TYPE, articleId: String): MutableLiveData<ChaptersCharacters>

    fun getGenres(dataType: UtilStrings.Companion.ARTICLE_GENRE_TYPE, articleId: String): MutableLiveData<Genres>

}