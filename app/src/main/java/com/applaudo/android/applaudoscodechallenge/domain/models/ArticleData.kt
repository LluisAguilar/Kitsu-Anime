package com.applaudo.android.applaudoscodechallenge.domain.models

import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings

data class ArticleData(
        val id:String,
        val imageUrl:String?,
        var title:String?,
        var articleType: Int,
        val canonicalTitle:String?,
        val showType:String?,
        val numberEpisodes:String?,
        val year:String?,
        val startDate:String?,
        val endDate:String?,
        val genresList:String?,
        val averageRating:String?,
        val ageRating:String?,
        val duration:String?,
        val airingStatus:String?,
        val synopsis:String?,
        val youtubeUrl:String?
) {
}