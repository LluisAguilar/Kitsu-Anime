package com.applaudo.android.applaudoscodechallenge.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favorites")
data class ArticlesFavoriteEntity(

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String,

    @ColumnInfo(name = "articleType")
    var articleType: Int,

    @ColumnInfo(name = "articleId")
    var articleId: Int

) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null

}