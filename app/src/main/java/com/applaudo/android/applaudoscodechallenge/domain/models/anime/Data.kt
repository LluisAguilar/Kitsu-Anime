package com.applaudo.android.applaudoscodechallenge.domain.models.anime

data class Data(
    val status: Boolean?,
    val attributes: Attributes?,
    val id: String,
    val links: LinksData?,
    val relationships: Relationships?,
    val type: String?,
    val errors: KitsuError?
)