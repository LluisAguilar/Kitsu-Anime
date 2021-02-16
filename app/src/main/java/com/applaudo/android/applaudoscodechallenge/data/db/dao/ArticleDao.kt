package com.applaudo.android.applaudoscodechallenge.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.applaudo.android.applaudoscodechallenge.data.db.entities.ArticlesFavoriteEntity

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(articlesFavoriteEntity: ArticlesFavoriteEntity)

    @Query("SELECT * FROM Favorites")
    fun getFavorites() : LiveData<List<ArticlesFavoriteEntity>>

    @Query("DELETE FROM Favorites WHERE articleId = :id")
    suspend fun deleteFavorite(id: Int)

}