package com.applaudo.android.applaudoscodechallenge.domain.models.anime


data class AnimeArticleDataUseCase(
    val status:Boolean,
    val data: List<Data>?,
    val errors: KitsuError?
)
