package com.applaudo.android.applaudoscodechallenge.utils

import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.Attributes
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.Data
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.Titles
import com.applaudo.android.applaudoscodechallenge.domain.models.AnimeData
import java.util.ArrayList

class UtilMethods {

    companion object {
        fun getAnimeData(article: List<Data>): ArrayList<AnimeData> {
            val animeDataList = arrayListOf<AnimeData>()
            for (x in 0 until article.size) {
                animeDataList.add(
                    AnimeData(
                        article.get(x).attributes?.posterImage?.original,
                        getTitle(article.get(x).attributes!!),
                        article.get(x).attributes?.canonicalTitle,
                        article.get(x).attributes?.showType,
                        article.get(x).attributes?.episodeCount.toString(),
                        article.get(x).attributes?.startDate!!.substring(0, 4),
                        article.get(x).attributes?.startDate,
                        article.get(x).attributes?.endDate,
                        article.get(x).relationships?.genres?.links?.related,
                        article.get(x).attributes?.averageRating,
                        article.get(x).attributes?.ageRating,
                        article.get(x).attributes?.episodeLength.toString(),
                        article.get(x).attributes?.status,
                        article.get(x).attributes?.synopsis,
                        article.get(x).attributes?.youtubeVideoId
                    )
                )
            }

            return animeDataList
        }

        fun getTitle(attributes: Attributes): String {
            return if (attributes.titles?.en != null) {
                attributes.titles.en
            } else if (attributes.titles?.en_us != null) {
                attributes.titles.en_us
            } else if (attributes.titles?.en_jp != null) {
                attributes.titles.en_jp
            } else if (attributes.titles?.ja_jp != null) {
                attributes.titles.ja_jp
            } else if (attributes.canonicalTitle != null) {
                attributes.canonicalTitle
            } else {
                "No title"
            }
        }

    }
}