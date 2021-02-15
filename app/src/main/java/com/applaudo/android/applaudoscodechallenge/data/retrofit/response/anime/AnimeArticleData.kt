package com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime

data class AnimeArticleData(
    val status:Boolean,
    val data: List<Data>?,
    val errors: KitsuError?
)