package com.applaudo.android.applaudoscodechallenge.data.retrofit.response

data class ArticleData(
    val status:Boolean,
    val data: List<Data>?,
    val errors: KitsuError?
)