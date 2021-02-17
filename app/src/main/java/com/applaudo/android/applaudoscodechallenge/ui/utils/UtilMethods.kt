package com.applaudo.android.applaudoscodechallenge.ui.utils

import android.widget.Toast
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.data.db.entities.ArticlesFavoriteEntity
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.Attributes
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.chapters_characters.Names
import com.applaudo.android.applaudoscodechallenge.data.retrofit.response.chapters_characters.Titles
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.FavoriteArticleData
import java.util.ArrayList

class UtilMethods {

    companion object {
        fun getAnimeData(article: List<com.applaudo.android.applaudoscodechallenge.data.retrofit.response.anime.Data>): ArrayList<ArticleData> {
            val animeDataList = arrayListOf<ArticleData>()
            for (x in 0 until article.size) {
                animeDataList.add(
                    ArticleData(
                        article.get(x).id,
                        article.get(x).attributes?.posterImage?.small,
                        getAnimeTitle(article.get(x).attributes!!),
                        UtilStrings.ANIME,
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
                        article.get(x).attributes?.youtubeVideoId,
                        article.get(x).relationships?.characters?.links?.related,
                        article.get(x).relationships?.episodes?.links?.related
                    )
                )
            }

            return animeDataList
        }

        private fun getArticleDate(startDate: String?): String {

            if (startDate != null) {
                return startDate.substring(0, 4)
            } else {
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
                MyApp.instance.getString(R.string.no_title)
            }
        }

        fun getMangaData(article: List<com.applaudo.android.applaudoscodechallenge.data.retrofit.response.manga.Data>): ArrayList<ArticleData> {
            val mangaDataList = arrayListOf<ArticleData>()
            for (x in 0 until article.size) {
                mangaDataList.add(
                    ArticleData(
                        article.get(x).id,
                        article.get(x).attributes.posterImage?.small,
                        getMangaTitle(article.get(x).attributes),
                        UtilStrings.MANGA,
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
                        "",
                        article.get(x).relationships.chapters.links.related,
                        article.get(x).relationships.chapters.links.related
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
            } else if (attributes.titles?.en_jp != null) {
                attributes.titles.en_jp
            } else if (attributes.titles?.cs_cz != null) {
                attributes.titles.cs_cz
            } else if (attributes.titles?.ar != null) {
                attributes.titles.ar
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
                MyApp.instance.getString(R.string.no_title)
            }

        }

        fun favoriteArticleEntityToFavoriteArticleModel(favoriteArticleEntity: List<ArticlesFavoriteEntity>?): ArrayList<FavoriteArticleData> {
            val favoritesArticleList = arrayListOf<FavoriteArticleData>()
            for (x in 0 until favoriteArticleEntity!!.size) {
                favoritesArticleList.add(
                    FavoriteArticleData(
                        favoriteArticleEntity.get(x).articleId.toString(),
                        favoriteArticleEntity.get(x).title,
                        favoriteArticleEntity.get(x).imageUrl,
                        favoriteArticleEntity.get(x).articleType
                    )
                )
            }
            return favoritesArticleList
        }

        fun makeToast(toastMessage: String) {
            Toast.makeText(MyApp.instance, toastMessage, Toast.LENGTH_SHORT).show()
        }

        fun getAnimeEpisodesUrl(articleId: String): String {
            return "anime?filter[id]=" + articleId + "&include=episodes"
        }

        fun getAnimeCharactersUrl(articleId: String): String {
            return "anime?filter[id]=" + articleId + "&include=characters.character"
        }

        fun getMangaCharactersUrl(articleId: String): String {
            return "manga?filter[id]=" + articleId + "&include=characters.character"
        }

        fun getMangaChaptersUtl(articleId: String): String {
            return "manga?filter[id]=" + articleId + "&include=chapters"
        }

        fun getMangaGenresUrl(articleId: String):String{
            return "manga/"+articleId+"/genres"
        }
        fun getAnimeGenresUrl(articleId: String):String{
            return "manga/"+articleId+"/genres"
        }

        fun getTitle(titles: Titles, articleType: Int): String {
            return if (titles.en != null) {
                titles.en
            } else if (titles.en_us != null) {
                titles.en_us
            } else if (titles.ar != null) {
                titles.ar
            } else if (titles.cs_cz != null) {
                titles.cs_cz
            } else if (titles.en_jp != null) {
                titles.en_jp
            } else if (titles.es_es != null) {
                titles.es_es
            } else if (titles.fa_ir != null) {
                titles.fa_ir
            } else if (titles.fi_fi != null) {
                titles.fi_fi
            } else if (titles.fr_fr != null) {
                titles.fr_fr
            } else if (titles.hr_hr != null) {
                titles.hr_hr
            } else if (titles.it_it != null) {
                titles.it_it
            } else if (titles.ja_jp != null) {
                titles.ja_jp
            } else if (titles.ko_kr != null) {
                titles.ko_kr
            } else if (titles.pt_br != null) {
                titles.pt_br
            } else if (titles.ru_ru != null) {
                titles.ru_ru
            } else if (titles.th_th != null) {
                titles.th_th
            } else if (titles.tr_tr != null) {
                titles.tr_tr
            } else if (titles.vi_vn != null) {
                titles.vi_vn
            } else if (titles.zh_cn != null) {
                titles.zh_cn
            } else if (articleType == UtilStrings.MANGA) {
                MyApp.instance.getString(R.string.chapter_title)
                }else {
                    MyApp.instance.getString(R.string.episode_title)
                }
        }

        fun getCharacterName(names: Names?): String {
            return if (names?.en != null) {
                names.en
            } else if (names?.en_us != null) {
                names.en_us
            } else if (names?.ar != null) {
                names.ar
            } else if (names?.cs_cz != null) {
                names.cs_cz
            } else if (names?.en_jp != null) {
                names.en_jp
            } else if (names?.es_es != null) {
                names.es_es
            } else if (names?.fa_ir != null) {
                names.fa_ir
            } else if (names?.fi_fi != null) {
                names.fi_fi
            } else if (names?.fr_fr != null) {
                names.fr_fr
            } else if (names?.hr_hr != null) {
                names.hr_hr
            } else if (names?.it_it != null) {
                names.it_it
            } else if (names?.ja_jp != null) {
                names.ja_jp
            } else if (names?.ko_kr != null) {
                names.ko_kr
            } else if (names?.pt_br != null) {
                names.pt_br
            } else if (names?.ru_ru != null) {
                names.ru_ru
            } else if (names?.th_th != null) {
                names.th_th
            } else if (names?.tr_tr != null) {
                names.tr_tr
            } else if (names?.vi_vn != null) {
                names.vi_vn
            } else if (names?.zh_cn != null) {
                names.zh_cn
            } else {
                MyApp.instance.getString(R.string.character_name)
            }
        }
    }
}