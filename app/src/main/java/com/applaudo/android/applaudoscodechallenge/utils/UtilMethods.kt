package com.applaudo.android.applaudoscodechallenge.utils

import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.Attributes
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import java.util.ArrayList

class UtilMethods {

    companion object {
        fun getAnimeData(article: List<com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.Data>): ArrayList<ArticleData> {
            val animeDataList = arrayListOf<ArticleData>()
            for (x in 0 until article.size) {
                animeDataList.add(
                    ArticleData(
                        article.get(x).id,
                        article.get(x).attributes?.posterImage?.original,
                        getAnimeTitle(article.get(x).attributes!!),
                        article.get(x).attributes?.canonicalTitle,
                        article.get(x).attributes?.showType,
                        article.get(x).attributes?.episodeCount.toString(),
                        getArticleDate(article.get(x).attributes?.startDate),
                        article.get(x).attributes?.startDate,
                        article.get(x).attributes?.endDate,
                        article.get(x).relationships?.genres?.links?.related,
                        article.get(x).attributes?.averageRating,
                        article.get(x).attributes?.ageRatingGuide,
                        article.get(x).attributes?.episodeLength.toString(),
                        article.get(x).attributes?.status,
                        article.get(x).attributes?.synopsis,
                        article.get(x).attributes?.youtubeVideoId
                    )
                )
            }

            return animeDataList
        }

        private fun getArticleDate(startDate: String?): String {

            if (startDate != null){
                return startDate.substring(0,4)
            }else {
                return "No date found"
            }

        }

        fun getAnimeTitle(attributes: Attributes): String {
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

        fun getMangaData(article: List<com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.Data>): ArrayList<ArticleData> {
            val mangaDataList = arrayListOf<ArticleData>()
            for (x in 0 until article.size) {
                mangaDataList.add(
                    ArticleData(
                        article.get(x).id,
                        article.get(x).attributes.posterImage?.original,
                        getMangaTitle(article.get(x).attributes),
                        article.get(x).attributes.canonicalTitle,
                        article.get(x).attributes.mangaType,
                        article.get(x).attributes.chapterCount.toString(),
                        getArticleDate(article.get(x).attributes.startDate),
                        article.get(x).attributes.startDate,
                        article.get(x).attributes.endDate,
                        article.get(x).relationships.genres.links.related,
                        article.get(x).attributes.averageRating,
                        article.get(x).attributes.ageRatingGuide,
                        null,
                        article.get(x).attributes.status,
                        article.get(x).attributes.synopsis,
                        ""
                    )
                )
            }

            return mangaDataList
        }

        fun getMangaTitle(attributes: com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.Attributes): String {
            return if (attributes.titles?.en != null) {
                attributes.titles.en
            } else if (attributes.titles?.en_us != null) {
                attributes.titles.en_us
            } else if (attributes.titles?.ar != null) {
                attributes.titles.ar
            } else if (attributes.titles?.cs_cz != null) {
                attributes.titles.cs_cz
            } else if (attributes.titles?.en_jp != null) {
                attributes.titles.en_jp
            } else if (attributes.titles?.es_es != null) {
                attributes.titles.es_es
            } else if (attributes.titles?.fa_ir != null) {
                attributes.titles.fa_ir
            } else if (attributes.titles?.fi_fi != null) {
                attributes.titles.fi_fi
            } else if (attributes.titles?.fr_fr != null) {
                attributes.titles.fr_fr
            } else if (attributes.titles?.hr_hr != null) {
                attributes.titles.hr_hr
            } else if (attributes.titles?.it_it != null) {
                attributes.titles.it_it
            } else if (attributes.titles?.ja_jp != null) {
                attributes.titles.ja_jp
            } else if (attributes.titles?.ko_kr != null) {
                attributes.titles.ko_kr
            } else if (attributes.titles?.pt_br != null) {
                attributes.titles.pt_br
            } else if (attributes.titles?.ru_ru != null) {
                attributes.titles.ru_ru
            } else if (attributes.titles?.th_th != null) {
                attributes.titles.th_th
            } else if (attributes.titles?.tr_tr != null) {
                attributes.titles.tr_tr
            } else if (attributes.titles?.vi_vn != null) {
                attributes.titles.vi_vn
            } else if (attributes.titles?.zh_cn != null) {
                attributes.titles.zh_cn
            } else {
                "No title"
            }

        }
    }
}