package com.applaudo.android.applaudoscodechallenge.data.retrofit.response.chapters_characters

data class Attributes(
    val airdate: String,
    val canonicalTitle: String,
    val createdAt: String,
    val description: String,
    val length: Int,
    val number: Int,
    val names: Names?,
    val name: String?,
    val relativeNumber: Int,
    val seasonNumber: Int,
    val synopsis: String,
    val titles: Titles,
    val updatedAt: String
)