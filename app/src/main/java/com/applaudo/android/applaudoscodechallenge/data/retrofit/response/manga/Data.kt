package com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga

data class Data(
    val attributes: Attributes,
    val id: String,
    val linksData: LinksData,
    val relationships: Relationships,
    val type: String
)