package com.applaudo.android.applaudoscodechallenge.domain.models

data class ArticleData(
        val id:String,
        val imageUrl:String?,
        var title:String?,
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