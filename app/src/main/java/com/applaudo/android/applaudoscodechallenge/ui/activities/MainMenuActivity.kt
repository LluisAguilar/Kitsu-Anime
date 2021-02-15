package com.applaudo.android.applaudoscodechallenge.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import com.applaudo.android.applaudoscodechallenge.ui.adapters.MenuPagerAdapter
import com.applaudo.android.applaudoscodechallenge.ui.alert.CustomAlerts
import com.applaudo.android.applaudoscodechallenge.domain.viewmodels.KitsuViewModel
import com.applaudo.android.applaudoscodechallenge.utils.UtilMethods
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.categoriesList
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IndexOutOfBoundsException

class MainMenuActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private lateinit var mAlerts: CustomAlerts
    private lateinit var mMenuPagerAdapter: MenuPagerAdapter
    private lateinit var mKitsuViewModel: KitsuViewModel

    private var mTrendingAnimeList = arrayListOf<ArticleData>()
    private var mCategoryAnimeList = arrayListOf<ArticleData>()
    private var mCategoriesAnimeCount = 0
    private var mCategoriesMangaCount = 0

    private var mTrendingMangaList = arrayListOf<ArticleData>()
    private var mCategoryMangaList = arrayListOf<ArticleData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mKitsuViewModel = ViewModelProvider(this).get(KitsuViewModel::class.java)
        mAlerts = CustomAlerts(this)

        mMenuPagerAdapter = MenuPagerAdapter(supportFragmentManager, this)
        mMenuPagerAdapter.saveState()
        menu_viewpager.adapter = mMenuPagerAdapter
        myflights_tabs.setupWithViewPager(menu_viewpager)
        menu_viewpager.addOnPageChangeListener(this)

        getTrendingAnimeData()
    }

    //Call Trending anime data
    fun getTrendingAnimeData() {
        if (mTrendingAnimeList.size == 0) {
            mKitsuViewModel.getAnime(UtilStrings.Companion.ANIME_DATA_TYPE.TRENDING, "", "", "","")
                .observe(this, {
                    if (it.status) {
                        mTrendingAnimeList.clear()
                        mTrendingAnimeList = UtilMethods.getAnimeData(it.data!!)
                        mMenuPagerAdapter.animeFragment.setTrendingAnime(mTrendingAnimeList)
                        //Once trending anime is called, we call finished anime
                        getOnAirAnimeData()
                    } else {
                        mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                    }

                })
        }
    }

    private fun getOnAirAnimeData() {
        mKitsuViewModel.getAnime(UtilStrings.Companion.ANIME_DATA_TYPE.ONAIR, "", "", "","")
            .observe(this, {
                if (it.status) {
                    mMenuPagerAdapter.animeFragment.setOnAirAnime(UtilMethods.getAnimeData(it.data!!))
                    getStreamersList()
                } else {
                    mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                }
            })
    }

    private fun getStreamersList() {
        mKitsuViewModel.getStreamersImage().observe(this, {
            mMenuPagerAdapter.animeFragment.setStreamersList(it)
            getAnimeByCategory()
        })
    }

    private fun getAnimeByCategory() {
        try {
            mKitsuViewModel.getAnime(
                UtilStrings.Companion.ANIME_DATA_TYPE.CATEGORIES,
                categoriesList.get(mCategoriesAnimeCount),
                "", "",""
            )
                .observe(this, {
                    if (it.status) {
                        if (mCategoriesAnimeCount == categoriesList.size) {
                            mMenuPagerAdapter.animeFragment.setCategoryAnime(mCategoryAnimeList)
                            mCategoriesAnimeCount = 0
                        } else {
                            mCategoryAnimeList.add(UtilMethods.getAnimeData(it.data!!).get(0))
                            mCategoryAnimeList.get(mCategoriesAnimeCount).title = categoriesList.get(mCategoriesAnimeCount)
                            mCategoriesAnimeCount++
                            getAnimeByCategory()
                        }
                    }
                })
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            mMenuPagerAdapter.animeFragment.setCategoryAnime(mCategoryAnimeList)
            mCategoriesAnimeCount = 0
        }

    }


    //Call Trending anime data
    fun getTrendingManga() {
        if (mTrendingMangaList.size == 0) {
            mKitsuViewModel.getManga(UtilStrings.Companion.MANGA_DATA_TYPE.TRENDING, "", "","")
                .observe(this, {
                    if (it.status) {
                        mTrendingMangaList.clear()
                        mTrendingMangaList = UtilMethods.getMangaData(it.data)
                        mMenuPagerAdapter.mangaFragment.setTrendingManga(mTrendingMangaList)
                        //Once trending manga is called, we call on air manga
                        getOnAirMangaData()
                    } else {
                        mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                    }

                })
        }
    }

    private fun getOnAirMangaData() {
        mKitsuViewModel.getManga(UtilStrings.Companion.MANGA_DATA_TYPE.ONAIR, "", "","")
            .observe(this, {
                if (it.status) {
                    mMenuPagerAdapter.mangaFragment.setOnAirManga(UtilMethods.getMangaData(it.data))
                    getFinishedMangaData()
                } else {
                    mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                }
            })
    }

    private fun getFinishedMangaData() {
        mKitsuViewModel.getManga(UtilStrings.Companion.MANGA_DATA_TYPE.FINISHED, "", "","")
            .observe(this, {
                if (it.status) {
                    mMenuPagerAdapter.mangaFragment.setFinishedManga(UtilMethods.getMangaData(it.data))
                    getMangaByCategory()
                } else {
                    mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                }
            })
    }

    private fun getMangaByCategory() {
        try {
            mKitsuViewModel.getManga(
                UtilStrings.Companion.MANGA_DATA_TYPE.CATEGORIES,
                categoriesList.get(mCategoriesMangaCount),
                "",""
            )
                .observe(this, {
                    if (it.status) {
                        if (mCategoriesMangaCount == categoriesList.size) {
                            mMenuPagerAdapter.mangaFragment.setCategoryAnime(mCategoryMangaList)
                            mCategoriesMangaCount = 0
                            getStreamersList()
                        } else {
                            mCategoryMangaList.add(UtilMethods.getMangaData(it.data).get(0))
                            mCategoryMangaList.get(mCategoriesMangaCount).title =
                                categoriesList.get(mCategoriesMangaCount)
                            mCategoriesMangaCount++
                            getMangaByCategory()
                        }
                    }
                })
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            mMenuPagerAdapter.mangaFragment.setCategoryAnime(mCategoryMangaList)
            mCategoriesMangaCount = 0
        }

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

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

            }
        }

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    fun getSearchTextAnime(newText: String?) {
        if (newText != null) {
            mKitsuViewModel.getAnime(UtilStrings.Companion.ANIME_DATA_TYPE.SEARCH_TEXT, "", newText,"","")
                .observe(this, {
                    if (it.status) {
                        mMenuPagerAdapter.animeFragment.setSearchedAnime(UtilMethods.getAnimeData(it.data!!))
                    } else {
                        mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                    }
                })
        }
    }

    fun getSearchTextManga(newText: String?) {
        if (newText != null) {
            mKitsuViewModel.getManga(UtilStrings.Companion.MANGA_DATA_TYPE.SEARCH_TEXT, "", newText,"")
                .observe(this, {
                    if (it.status) {
                        mMenuPagerAdapter.mangaFragment.setSearchedManga(UtilMethods.getMangaData(it.data))
                    } else {
                        mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
                    }
                })
        }
    }

    fun getSearchAnimeByCategory(category: String) {
        mKitsuViewModel.getAnime(
            UtilStrings.Companion.ANIME_DATA_TYPE.CATEGORIES_SEARCH,
            category,
            "","",""
        ).observe(this, {
            if (it.status) {
                mMenuPagerAdapter.animeFragment.setCategorySearched(UtilMethods.getAnimeData(it.data!!))
            } else {
                mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
            }
        })
    }

    fun getSearchAnimeByStreamer(streamer: String) {
        mKitsuViewModel.getAnime(UtilStrings.Companion.ANIME_DATA_TYPE.STREAMER, "", "","", streamer).observe(this, {
            if (it.status) {
                mMenuPagerAdapter.animeFragment.setCategorySearched(UtilMethods.getAnimeData(it.data!!))
            } else {
                mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
            }
        })
    }

    fun getSearchMangaByCategory(category: String) {
        mKitsuViewModel.getManga(
            UtilStrings.Companion.MANGA_DATA_TYPE.CATEGORIES_SEARCH,
            category,
            "",""
        ).observe(this, {
            if (it.status) {
                mMenuPagerAdapter.mangaFragment.setCategorySearched(UtilMethods.getMangaData(it.data))
            } else {
                mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail.toString())
            }
        })
    }
}