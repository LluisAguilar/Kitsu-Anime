package com.applaudo.android.applaudoscodechallenge.ui.viewmodels

import com.applaudo.android.applaudoscodechallenge.domain.utils.UtilStringsDomain
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings

fun animeDataToAnimeDataDomain(dataTypeUi: UtilStrings.Companion.ANIME_DATA_TYPE): UtilStringsDomain.Companion.ANIME_DATA_TYPE {
    var domainType:UtilStringsDomain.Companion.ANIME_DATA_TYPE? = UtilStringsDomain.Companion.ANIME_DATA_TYPE.TRENDING

    when(dataTypeUi){

        UtilStrings.Companion.ANIME_DATA_TYPE.TRENDING -> {
                domainType = UtilStringsDomain.Companion.ANIME_DATA_TYPE.TRENDING
        }

        UtilStrings.Companion.ANIME_DATA_TYPE.ONAIR -> {
            domainType = UtilStringsDomain.Companion.ANIME_DATA_TYPE.ONAIR
        }

        UtilStrings.Companion.ANIME_DATA_TYPE.CATEGORIES -> {
            domainType = UtilStringsDomain.Companion.ANIME_DATA_TYPE.CATEGORIES
        }

        UtilStrings.Companion.ANIME_DATA_TYPE.CATEGORIES_SEARCH -> {
            domainType = UtilStringsDomain.Companion.ANIME_DATA_TYPE.CATEGORIES_SEARCH
        }

        UtilStrings.Companion.ANIME_DATA_TYPE.SEARCH_TEXT -> {
            domainType = UtilStringsDomain.Companion.ANIME_DATA_TYPE.SEARCH_TEXT
        }

        UtilStrings.Companion.ANIME_DATA_TYPE.INDIVIDUAL -> {
            domainType = UtilStringsDomain.Companion.ANIME_DATA_TYPE.INDIVIDUAL
        }

        UtilStrings.Companion.ANIME_DATA_TYPE.STREAMER -> {
            domainType = UtilStringsDomain.Companion.ANIME_DATA_TYPE.STREAMER
        }

    }

    return domainType
}