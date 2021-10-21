package com.applaudo.android.applaudoscodechallenge.ui.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleChapterData
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import com.applaudo.android.applaudoscodechallenge.ui.viewmodels.ArticleViewModel
import com.applaudo.android.applaudoscodechallenge.ui.alert.CustomAlerts
import com.applaudo.android.applaudoscodechallenge.ui.fragments.BottomSheetArticleFragment
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilMethods
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
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
    private lateinit var bottomSheetArticleFragment: BottomSheetArticleFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aticle_detail)
        overridePendingTransition(R.anim.slide_in_right_to_left, R.anim.slide_out_right_to_left)

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
                    UtilMethods.makeToast(getString(R.string.removed_from_favorites))
                } else {
                    mArticleViewModel.insertFavoriteArticle(mMangaData)
                    UtilMethods.makeToast(getString(R.string.added_to_afvorites))
                }
            } else {
                if (mFavorite) {
                    mArticleViewModel.deleteFavoriteArticle(mAnimeData.id.toInt())
                    isFavorite(mAnimeData)
                    UtilMethods.makeToast(getString(R.string.removed_from_favorites))
                } else {
                    mArticleViewModel.insertFavoriteArticle(mAnimeData)
                    UtilMethods.makeToast(getString(R.string.added_to_afvorites))
                }
            }
        }

        article_detail_chapters.setOnClickListener {
            var articleType = UtilStrings.Companion.ARTICLE_DATA_TYPE.ANIME_EPISODES
            if (mArticleType == UtilStrings.MANGA) {
                articleType = UtilStrings.Companion.ARTICLE_DATA_TYPE.MANGA_CHAPTERS
            }
            mAlert.startAlertProgress("")
            mArticleViewModel.getEpisodesCharacters(
                articleType,
                mArticleId
            ).observe(this, {
                mAlert.stopAlertProgress()
                if (it.status) {
                    if (it.included!!.size > 0) {
                        val articleChaptersList = arrayListOf<ArticleChapterData>()
                        for (x in 0 until it.included.size) {
                            articleChaptersList.add(
                                ArticleChapterData(
                                    (x + 1).toString(),
                                    UtilMethods.getTitle(
                                        it.included.get(x).attributes.titles,
                                        mArticleType
                                    )
                                )
                            )
                        }

                        bottomSheetArticleFragment =
                            BottomSheetArticleFragment(articleChaptersList)
                        bottomSheetArticleFragment.show(
                            supportFragmentManager,
                            "bottom_chapters"
                        )
                    } else {
                        UtilMethods.makeToast(getString(R.string.no_episodes_found))
                    }
                } else {
                    mAlert.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                }

            })
        }

        article_detail_characters.setOnClickListener {
            var articleType = UtilStrings.Companion.ARTICLE_DATA_TYPE.ANIME_CHARACTERS
            if (mArticleType == UtilStrings.MANGA) {
                articleType = UtilStrings.Companion.ARTICLE_DATA_TYPE.MANGA_CHARACTERS
            }
            mAlert.startAlertProgress("")
            mArticleViewModel.getEpisodesCharacters(articleType, mArticleId).observe(this, {
                mAlert.stopAlertProgress()
                if (it.status) {
                    val articleChaptersList = arrayListOf<ArticleChapterData>()
                    var itemNum = 1
                    if (it.included != null) {
                        if (it.included.size > 0) {
                            for (x in (it.included.size / 2) + 1 until it.included.size) {
                                articleChaptersList.add(
                                    ArticleChapterData(
                                        itemNum.toString(),
                                        UtilMethods.getCharacterName(it.included.get(x).attributes.names)
                                    )
                                )
                                itemNum++
                            }
                            bottomSheetArticleFragment =
                                BottomSheetArticleFragment(articleChaptersList)
                            bottomSheetArticleFragment.show(
                                supportFragmentManager,
                                "bottom_chapters"
                            )
                        } else {
                            UtilMethods.makeToast(getString(R.string.no_characters_found))
                        }
                    } else {
                        UtilMethods.makeToast(getString(R.string.no_characters_found))
                    }
                } else {
                    mAlert.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                }

            })
        }

    }

    override fun finish() {
        overridePendingTransition(R.anim.slide_in_left_to_right, R.anim.slide_out_left_to_right)
        super.finish()
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
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
            } else {
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
            )
        } else {
            article_synopsis_tv.setText(Html.fromHtml(articleData.synopsis));
        }

        if (articleData.youtubeUrl != null) {
            if (!articleData.youtubeUrl.equals("")) {
                getLifecycle().addObserver(article_detail_youtube_pv)
                article_detail_youtube_pv.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(articleData.youtubeUrl, 0f)
                        youTubePlayer.pause()
                    }

                    override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerConstants.PlayerState) {
                        if(state == PlayerConstants.PlayerState.PLAYING){
                            article_share_fab.hide()
                        }else if (state == PlayerConstants.PlayerState.PAUSED){
                            article_share_fab.show()
                        }
                        super.onStateChange(youTubePlayer, state)
                    }
                })
            } else {
                article_youtube_layout.visibility = View.GONE
            }
        } else {
            article_youtube_layout.visibility = View.GONE
        }


        var articleType = UtilStrings.Companion.ARTICLE_GENRE_TYPE.ANIME_GENRES
        if (mArticleType == UtilStrings.MANGA) {
            articleType = UtilStrings.Companion.ARTICLE_GENRE_TYPE.MANGA_GENRES
        }
        mArticleViewModel.getGenres(articleType, mArticleId).observe(this, {
            if (it.status) {
                if (it.data != null) {
                    if (it.data.size > 0) {
                        val ssb = StringBuilder()
                        for (x in 0 until it.data.size) {
                            if (x == 0){
                                ssb.append(it.data.get(x).attributes.name)
                            }else {
                                ssb.append(", " + it.data.get(x).attributes.name)
                            }
                        }
                        article_genres_list_tv.setText(ssb)
                        article_genres_detail.visibility = View.VISIBLE
                    }
                }
            }
        })


        actionShareFab(articleData.title)

    }

    private fun actionShareFab(title: String?) {
        article_share_fab.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, title)
            startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
        }
    }
}