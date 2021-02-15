package com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga

data class Attributes(
    val abbreviatedTitles: List<String>?,
    val ageRating: String?,
    val ageRatingGuide: String?,
    val averageRating: String?,
    val canonicalTitle: String?,
    val chapterCount: Any?,
    val coverImage: CoverImage?,
    val coverImageTopOffset: Int?,
    val createdAt: String?,
    val description: String?,
    val endDate: String?,
    val favoritesCount: Int?,
    val mangaType: String?,
    val nextRelease: Any?,
    val popularityRank: Int?,
    val posterImage: PosterImage?,
    val ratingFrequencies: RatingFrequencies?,
    val ratingRank: Int?,
    val serialization: String?,
    val slug: String?,
    val startDate: String?,
    val status: String?,
    val subtype: String?,
    val synopsis: String?,
    val tba: Any?,
    val titles: Titles?,
    val updatedAt: String?,
    val userCount: Int?,
    val volumeCount: Int?
)