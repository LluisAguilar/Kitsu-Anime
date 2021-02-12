package com.applaudo.android.applaudoscodechallenge.data.retrofit.response

data class Data(
    val attributes: Attributes,
    val id: String,
    val links: Links,
    val relationships: Relationships,
    val type: String
)