package com.applaudo.android.applaudoscodechallenge.utils

class UtilStrings {

    companion object {

        const val BASE_URL = "https://kitsu.io/api/edge/"

        // CATEGORIES
        val categoriesList = arrayListOf("action","comedy","drama","fantasy","romance","crime","friendship","military","politics","sports")

        //URL for categories anime
        const val CATEGORIES_URL = "anime?page[limit]=1&page[offset]=0&filter[categories]="

        enum class ANIME_DATA_TYPE{
            TRENDING,
            ONAIR,
            CATEGORIES
        }

    }
}