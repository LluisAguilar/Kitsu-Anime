package com.applaudo.android.applaudoscodechallenge.data.retrofit.response.genres

import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.Error

data class Genres(
    val status:Boolean,
    val errors:List<Error>?,
    val data: List<Data>?
)