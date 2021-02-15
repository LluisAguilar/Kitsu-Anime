package com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga

import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.KitsuError

data class MangaArticleData(
    val status:Boolean,
    val data: List<Data>,
    val errors: KitsuError?
)