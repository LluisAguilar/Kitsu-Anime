package com.applaudo.android.applaudoscodechallenge.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import com.applaudo.android.applaudoscodechallenge.ui.adapters.MenuPagerAdapter
import com.applaudo.android.applaudoscodechallenge.ui.alert.CustomAlerts
import com.applaudo.android.applaudoscodechallenge.domain.viewmodels.ArticleViewModel
import com.applaudo.android.applaudoscodechallenge.ui.fragments.AnimeFragment
import com.applaudo.android.applaudoscodechallenge.ui.fragments.FavoritesFragment
import com.applaudo.android.applaudoscodechallenge.ui.fragments.MangaFragment
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilMethods
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings.Companion.categoriesList
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.IndexOutOfBoundsException

class MainMenuActivity : AppCompatActivity() {

    private lateinit var mAlerts: CustomAlerts
    private lateinit var mMenuPagerAdapter: MenuPagerAdapter
    private lateinit var mArticleViewModel: ArticleViewModel

    private var mTrendingAnimeList = arrayListOf<ArticleData>()
    private var mCategoryAnimeList = arrayListOf<ArticleData>()
    private var mCategoriesAnimeCount = 0
    private var mCategoriesMangaCount = 0

    private var mTrendingMangaList = arrayListOf<ArticleData>()
    private var mCategoryMangaList = arrayListOf<ArticleData>()

    private val animeFragment = AnimeFragment.getInstance()
    private val mangaFragment = MangaFragment.getInstance()
    private val favoritesFragment = FavoritesFragment.getInstance()

    private val fragmentList = listOf(animeFragment, mangaFragment, favoritesFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mArticleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        mAlerts = CustomAlerts(this)

        mMenuPagerAdapter = MenuPagerAdapter(fragmentList, this)

        menu_viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        menu_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (position) {

                    0 -> {
                        getTrendingAnimeData()
                    }

                    1 -> {
                        getTrendingManga()
                    }
                    2 -> {
                        getFavoriteArticles()
                    }
                }

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        menu_viewpager.adapter = mMenuPagerAdapter

        //@TabLayoutMediator used to set pager TAB Title
        TabLayoutMediator(myflights_tabs, menu_viewpager) { tab, position ->
            tab.text = getString(R.string.anime)
            if (position == 0) {
                tab.text = getString(R.string.anime)
            } else if (position == 1) {
                tab.text = getString(R.string.manga)
            } else if (position == 2) {
                tab.text = getString(R.string.favorites)
            }
        }.attach()

        getTrendingAnimeData()
    }

    //Call Trending anime data
    fun getTrendingAnimeData() {
        if (mTrendingAnimeList.size == 0) {
            mArticleViewModel.getAnime(
                UtilStrings.Companion.ANIME_DATA_TYPE.TRENDING,
                "",
                "",
                "",
                ""
            )
                .observe(this, {
                    if (it.status) {
                        mTrendingAnimeList.clear()
                        mTrendingAnimeList = UtilMethods.getAnimeData(it.data!!)
                        animeFragment.setTrendingAnime(mTrendingAnimeList)
                        //Once trending anime is called, we call finished anime
                        getOnAirAnimeData()
                    } else {
                        mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                    }

                })
        } else {
            animeFragment.setLoadersInvisible()
        }
    }

    private fun getOnAirAnimeData() {
        mArticleViewModel.getAnime(UtilStrings.Companion.ANIME_DATA_TYPE.ONAIR, "", "", "", "")
            .observe(this, {
                if (it.status) {
                    animeFragment.setOnAirAnime(UtilMethods.getAnimeData(it.data!!))
                    getStreamersList()
                } else {
                    mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                }
            })
    }

    private fun getStreamersList() {
        mArticleViewModel.getStreamersImage().observe(this, {
            animeFragment.setStreamersList(it)
            getAnimeByCategory()
        })
    }

    private fun getAnimeByCategory() {
        try {
            mArticleViewModel.getAnime(
                UtilStrings.Companion.ANIME_DATA_TYPE.CATEGORIES,
                categoriesList.get(mCategoriesAnimeCount),
                "", "", ""
            )
                .observe(this, {
                    if (it.status) {
                        if (mCategoriesAnimeCount == categoriesList.size) {
                            animeFragment.setCategoryAnime(mCategoryAnimeList)
                            mCategoriesAnimeCount = 0
                        } else {
                            mCategoryAnimeList.add(UtilMethods.getAnimeData(it.data!!).get(0))
                            mCategoryAnimeList.get(mCategoriesAnimeCount).title =
                                categoriesList.get(mCategoriesAnimeCount)
                            mCategoriesAnimeCount++
                            getAnimeByCategory()
                        }
                    }
                })
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            animeFragment.setCategoryAnime(mCategoryAnimeList)
            mCategoriesAnimeCount = 0
        }

    }


    //Call Trending anime data
    fun getTrendingManga() {
        if (mTrendingMangaList.size == 0) {
            mArticleViewModel.getManga(UtilStrings.Companion.MANGA_DATA_TYPE.TRENDING, "", "", "")
                .observe(this, {
                    if (it.status) {
                        mTrendingMangaList.clear()
                        mTrendingMangaList = UtilMethods.getMangaData(it.data)
                        mangaFragment.setTrendingManga(mTrendingMangaList)
                        //Once trending manga is called, we call on air manga
                        getOnAirMangaData()
                    } else {
                        mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                    }

                })
        } else {
            mangaFragment.setLoadersInvisible()
        }
    }

    private fun getOnAirMangaData() {
        mArticleViewModel.getManga(UtilStrings.Companion.MANGA_DATA_TYPE.ONAIR, "", "", "")
            .observe(this, {
                if (it.status) {
                    mangaFragment.setOnAirManga(UtilMethods.getMangaData(it.data))
                    getFinishedMangaData()
                } else {
                    mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                }
            })
    }

    private fun getFinishedMangaData() {
        mArticleViewModel.getManga(UtilStrings.Companion.MANGA_DATA_TYPE.FINISHED, "", "", "")
            .observe(this, {
                if (it.status) {
                    mangaFragment.setFinishedManga(UtilMethods.getMangaData(it.data))
                    getMangaByCategory()
                } else {
                    mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                }
            })
    }

    private fun getMangaByCategory() {
        try {
            mArticleViewModel.getManga(
                UtilStrings.Companion.MANGA_DATA_TYPE.CATEGORIES,
                categoriesList.get(mCategoriesMangaCount),
                "", ""
            )
                .observe(this, {
                    if (it.status) {
                        if (mCategoriesMangaCount == categoriesList.size) {
                            mangaFragment.setCategoryAnime(mCategoryMangaList)
                            mCategoriesMangaCount = 0
                            getStreamersList()
                        } else {
                            if (it.data.size > 0){
                                mCategoryMangaList.add(UtilMethods.getMangaData(it.data).get(0))
                                mCategoryMangaList.get(mCategoriesMangaCount).title =
                                    categoriesList.get(mCategoriesMangaCount)
                                mCategoriesMangaCount++
                                getMangaByCategory()
                            }
                        }
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
            mangaFragment.setCategoryAnime(mCategoryMangaList)
            mCategoriesMangaCount = 0
        }

    }

    private fun getFavoriteArticles() {
        mArticleViewModel.getFavorites().observe(this, {
            favoritesFragment.setFavoriteArticlesInView(
                UtilMethods.favoriteArticleEntityToFavoriteArticleModel(
                    it
                )
            )
        })
    }

    fun getSearchTextAnime(newText: String?) {
        if (newText != null) {
            mArticleViewModel.getAnime(
                UtilStrings.Companion.ANIME_DATA_TYPE.SEARCH_TEXT,
                "",
                newText,
                "",
                ""
            )
                .observe(this, {
                    if (it.status) {
                        animeFragment.setSearchedAnime(UtilMethods.getAnimeData(it.data!!))
                    } else {
                        mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                    }
                })
        }
    }

    fun getSearchTextManga(newText: String?) {
        if (newText != null) {
            mArticleViewModel.getManga(
                UtilStrings.Companion.MANGA_DATA_TYPE.SEARCH_TEXT,
                "",
                newText,
                ""
            )
                .observe(this, {
                    if (it.status) {
                        mangaFragment.setSearchedManga(UtilMethods.getMangaData(it.data))
                    } else {
                        mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                    }
                })
        }
    }

    fun getSearchAnimeByCategory(category: String) {
        mArticleViewModel.getAnime(
            UtilStrings.Companion.ANIME_DATA_TYPE.CATEGORIES_SEARCH,
            category,
            "", "", ""
        ).observe(this, {
            if (it.status) {
                animeFragment.setCategorySearched(UtilMethods.getAnimeData(it.data!!))
            } else {
                mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
            }
        })
    }

    fun getSearchAnimeByStreamer(streamer: String) {
        mArticleViewModel.getAnime(
            UtilStrings.Companion.ANIME_DATA_TYPE.STREAMER,
            "",
            "",
            "",
            streamer
        ).observe(this, {
            if (it.status) {
                animeFragment.setCategorySearched(UtilMethods.getAnimeData(it.data!!))
            } else {
                mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
            }
        })
    }

    fun getSearchMangaByCategory(category: String) {
        mArticleViewModel.getManga(
            UtilStrings.Companion.MANGA_DATA_TYPE.CATEGORIES_SEARCH,
            category,
            "", ""
        ).observe(this, {
            if (it.status) {
                mangaFragment.setCategorySearched(UtilMethods.getMangaData(it.data))
            } else {
                mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
            }
        })
    }

    override fun onBackPressed() {
        if (menu_viewpager.currentItem == 0) {
            super.onBackPressed()
        } else if (menu_viewpager.currentItem == 1) {
            menu_viewpager.setCurrentItem(0)
        } else if (menu_viewpager.currentItem == 2) {
            menu_viewpager.setCurrentItem(1)
        }
    }
}