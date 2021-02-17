package com.applaudo.android.applaudoscodechallenge.ui.utils

class UtilStrings {

    companion object {

        const val BASE_URL = "https://kitsu.io/api/edge/"

        // CATEGORIES
        val categoriesList = arrayListOf(
            "action",
            "comedy",
            "drama",
            "fantasy",
            "romance",
            "crime",
            "friendship",
            "military",
            "politics",
            "sports"
        )

        //URL for requests
        const val ANIME_CATEGORIES_URL = "anime?page[limit]=1&page[offset]=0&filter[categories]="
        const val MANGA_CATEGORIES_URL = "manga?page[limit]=1&page[offset]=0&filter[categories]="
        const val ANIME_TEXT_SEARCH_URL = "anime?page[limit]=20&page[offset]=0&filter[text]="
        const val ANIME_CATEGORY_TEXT_SEARCH_URL =
            "anime?page[limit]=20&page[offset]=0&filter[categories]="
        const val MANGA_CATEGORY_TEXT_SEARCH_URL =
            "manga?page[limit]=20&page[offset]=0&filter[categories]="
        const val MANGA_TEXT_SEARCH_URL = "manga?page[limit]=20&page[offset]=0&filter[text]="
        const val ANIME_BY_ID_URL = "anime?filter[id]="
        const val ANIME_BY_STREAMER_URL = "anime?page[limit]=20&page[offset]=0&filter[streamers]="
        const val MANGA_BY_ID_URL = "manga?filter[id]="

        //Articles type
        const val ANIME = 0
        const val MANGA = 1

        enum class ANIME_DATA_TYPE {
            TRENDING,
            ONAIR,
            CATEGORIES,
            CATEGORIES_SEARCH,
            SEARCH_TEXT,
            INDIVIDUAL,
            STREAMER
        }

        enum class MANGA_DATA_TYPE {
            TRENDING,
            ONAIR,
            FINISHED,
            CATEGORIES,
            SEARCH_TEXT,
            CATEGORIES_SEARCH,
            INDIVIDUAL
        }

        enum class ARTICLE_DATA_TYPE {
            ANIME_EPISODES,
            ANIME_CHARACTERS,
            MANGA_CHAPTERS,
            MANGA_CHARACTERS
        }

        enum class ARTICLE_GENRE_TYPE {
            ANIME_GENRES,
            MANGA_GENRES
        }

    }
}