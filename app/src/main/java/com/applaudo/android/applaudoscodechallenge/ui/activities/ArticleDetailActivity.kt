package com.applaudo.android.applaudoscodechallenge.ui.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.ViewModelProvider
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import com.applaudo.android.applaudoscodechallenge.domain.viewmodels.ArticleViewModel
import com.applaudo.android.applaudoscodechallenge.ui.alert.CustomAlerts
import com.applaudo.android.applaudoscodechallenge.utils.UtilMethods
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_aticle_detail.*
import java.util.*

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var mArticleViewModel: ArticleViewModel
    private var mArticleType = UtilStrings.ANIME
    private var mArticleId = "0"
    private lateinit var mAlert: CustomAlerts
    private lateinit var mAnimeData: ArticleData
    private lateinit var mMangaData: ArticleData
    private var mFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aticle_detail)

        mArticleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        mAlert = CustomAlerts(this)

        mArticleType = intent.getIntExtra("article_type", UtilStrings.ANIME)
        mArticleId = intent.getStringExtra("article_id").toString()

        if (mArticleType == UtilStrings.MANGA) {
            getMangaData()
        } else {
            getAnimeData()
        }

        detail_back_arrow.setOnClickListener {
            onBackPressed()
        }

        article_favorite_iv.setOnClickListener {

            if (mArticleType == UtilStrings.MANGA) {
                if (mFavorite) {
                    mArticleViewModel.deleteFavoriteArticle(mMangaData.id.toInt())
                    isFavorite(mMangaData)
                }else {
                    mArticleViewModel.insertFavoriteArticle(mMangaData)
                }
            } else {
                if (mFavorite) {
                    mArticleViewModel.deleteFavoriteArticle(mAnimeData.id.toInt())
                    isFavorite(mAnimeData)
                }else {
                    mArticleViewModel.insertFavoriteArticle(mAnimeData)
                }
            }
        }

    }

    private fun getMangaData() {
        mArticleViewModel.getManga(
            UtilStrings.Companion.MANGA_DATA_TYPE.INDIVIDUAL,
            "",
            "",
            mArticleId
        ).observe(this, {
            if (it.status) {
                mMangaData = UtilMethods.getMangaData(it.data).get(0)
                setValuesInView(mMangaData)
                isFavorite(mMangaData)
            } else {
                mAlert.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
            }
        })
    }

    private fun getAnimeData() {
        mAlert.startAlertProgress("")
        mArticleViewModel.getAnime(
            UtilStrings.Companion.ANIME_DATA_TYPE.INDIVIDUAL,
            "",
            "",
            mArticleId,
            ""
        ).observe(this, {
            mAlert.stopAlertProgress()
            if (it.status) {
                mAnimeData = UtilMethods.getAnimeData(it.data!!).get(0)
                setValuesInView(mAnimeData)
                isFavorite(mAnimeData)
            } else {
                mAlert.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
            }
        })
    }

    private fun isFavorite(articleData: ArticleData) {
        mArticleViewModel.getFavorites().observe(this, {
            val articlesList = UtilMethods.favoriteArticleEntityToFavoriteArticleModel(it)
            if (articlesList.size > 0) {
                for (x in 0 until articlesList.size) {
                    if (articleData.id.equals(articlesList.get(x).id)) {
                        article_favorite_iv.setImageResource(R.drawable.ic_favorite_true)
                        mFavorite = true
                        break
                    } else {
                        article_favorite_iv.setImageResource(R.drawable.ic_favorite_false)
                        mFavorite = false
                    }
                }
            }else {
                article_favorite_iv.setImageResource(R.drawable.ic_favorite_false)
                mFavorite = false
            }
        })
    }

    private fun setValuesInView(articleData: ArticleData) {

        Picasso.get().load(articleData.imageUrl).into(article_detail_iv)
        article_title_tv.text = articleData.title
        article_canonical_title_tv.text = articleData.canonicalTitle
        if (articleData.numberEpisodes.equals("null")) {
            article_type_tv.text = articleData.showType
        } else {
            article_type_tv.text =
                String.format(articleData.showType + ", " + getString(R.string.episodes) + " " + articleData.numberEpisodes)
        }

        if (articleData.endDate == null) {
            article_date_tv.text =
                String.format(articleData.startDate + " " + getString(R.string.to) + " " + articleData.airingStatus)
        } else {
            article_date_tv.text =
                String.format(articleData.startDate + " " + getString(R.string.to) + " " + articleData.endDate)
        }
        article_genres_list_tv.text = articleData.genresList
        if (articleData.averageRating == null) {
            article_rating_tv.text = ""
        } else {
            article_rating_tv.text = articleData.averageRating + " " + "%"
        }
        article_age_rating_tv.text = articleData.ageRating
        if (articleData.duration == null) {
            article_duration_tv.text = ""
        } else {
            article_duration_tv.text =
                String.format(articleData.duration + " " + getString(R.string.minutes_each))
        }
        article_airing_status_tv.text = articleData.airingStatus?.capitalize(Locale.ROOT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            article_synopsis_tv.setText(
                Html.fromHtml(
                    articleData.synopsis,
                    Html.FROM_HTML_MODE_COMPACT
                )
            );
        } else {
            article_synopsis_tv.setText(Html.fromHtml(articleData.synopsis));
        }


    }
}