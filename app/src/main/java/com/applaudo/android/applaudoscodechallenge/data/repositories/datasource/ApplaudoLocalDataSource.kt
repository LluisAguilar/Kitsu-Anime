package com.applaudo.android.applaudoscodechallenge.data.repositories.datasource

import android.app.Application
import androidx.lifecycle.LiveData
import com.applaudo.android.applaudoscodechallenge.data.db.ApplaudoBD
import com.applaudo.android.applaudoscodechallenge.data.db.dao.ArticleDao
import com.applaudo.android.applaudoscodechallenge.data.db.entities.ArticlesFavoriteEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ApplaudoLocalDataSource(application:Application) {

    private var mApplaudoBD : ApplaudoBD? = null
    private var mArticleDao:ArticleDao? = null


    init {
     mApplaudoBD = ApplaudoBD.getDatabaseClient(application)
        mArticleDao = mApplaudoBD!!.articleDao()
    }


    fun insertFavorite(articleFavorite:ArticlesFavoriteEntity){
        CoroutineScope(IO).launch {
            mArticleDao?.insertFavorite(articleFavorite)
        }
    }

    fun getFavorites():LiveData<List<ArticlesFavoriteEntity>>{
        return mArticleDao!!.getFavorites()
    }

    fun deleteFavoriteById(id:Int){
        CoroutineScope(IO).launch {
            mArticleDao!!.deleteFavorite(id)
        }
    }


}