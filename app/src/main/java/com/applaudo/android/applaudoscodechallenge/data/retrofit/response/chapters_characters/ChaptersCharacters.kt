package com.applaudo.android.applaudoscodechallenge.data.retrofit.response.chapters_characters

import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.KitsuError

data class ChaptersCharacters(
    val status:Boolean,
    val errors: KitsuError?,
    val included: List<Included>?
)