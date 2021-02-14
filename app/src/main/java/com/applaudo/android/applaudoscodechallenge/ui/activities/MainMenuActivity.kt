package com.applaudo.android.applaudoscodechallenge.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.AnimeData
import com.applaudo.android.applaudoscodechallenge.ui.adapters.MenuPagerAdapter
import com.applaudo.android.applaudoscodechallenge.ui.alert.CustomAlerts
import com.applaudo.android.applaudoscodechallenge.domain.viewmodels.KitsuViewModel
import com.applaudo.android.applaudoscodechallenge.utils.UtilMethods
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.ANIME_DATA_TYPE.*
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings.Companion.categoriesList
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IndexOutOfBoundsException
import java.util.ArrayList

class MainMenuActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private lateinit var mAlerts: CustomAlerts
    private lateinit var mMenuPagerAdapter: MenuPagerAdapter
    private lateinit var mKitsuViewModel: KitsuViewModel

    private var mTrendingAnimeList = arrayListOf<AnimeData>()
    private var mCategoryAnimeList = arrayListOf<AnimeData>()
    private var mCategoriesCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mKitsuViewModel = ViewModelProvider(this).get(KitsuViewModel::class.java)
        mAlerts = CustomAlerts(this)

        mMenuPagerAdapter = MenuPagerAdapter(supportFragmentManager, this)
        menu_viewpager.adapter = mMenuPagerAdapter
        myflights_tabs.setupWithViewPager(menu_viewpager)
        menu_viewpager.addOnPageChangeListener(this)

        getTrendingAnimeData()
    }

    //Call Trending anime data
    fun getTrendingAnimeData() {
        if (mTrendingAnimeList.size == 0) {
            mKitsuViewModel.getAnime(TRENDING, "").observe(this, {
                if (it.status) {
                    mTrendingAnimeList.clear()
                    mTrendingAnimeList = UtilMethods.getAnimeData(it.data!!)
                    mMenuPagerAdapter.animeFragment.setTrendingAnime(mTrendingAnimeList)
                    //Once trending anime is called, we call finished anime
                    getOnAirAnimeData()
                } else {
                    mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail!!)
                }

            })
        }
    }

    private fun getOnAirAnimeData() {
        mKitsuViewModel.getAnime(ONAIR, "").observe(this, {
            if (it.status) {
                mMenuPagerAdapter.animeFragment.setOnAirAnime(UtilMethods.getAnimeData(it.data!!))
                getAnimeByCategory()
            } else {
                mAlerts.alertInformation(it.errors?.errors?.get(0)?.detail!!)
            }
        })
    }

    private fun getAnimeByCategory() {
        try {
            mKitsuViewModel.getAnime(CATEGORIES, categoriesList.get(mCategoriesCount))
                .observe(this, {
                    if (it.status) {
                        if (mCategoriesCount == categoriesList.size) {
                            mMenuPagerAdapter.animeFragment.setCategoryAnime(mCategoryAnimeList)
                        } else {
                            mCategoryAnimeList.add(UtilMethods.getAnimeData(it.data!!).get(0))
                            mCategoryAnimeList.get(mCategoriesCount).title =
                                categoriesList.get(mCategoriesCount)
                            mCategoriesCount++
                            getAnimeByCategory()
                        }
                    }
                })
        }catch (e:IndexOutOfBoundsException){
            e.printStackTrace()
            mMenuPagerAdapter.animeFragment.setCategoryAnime(mCategoryAnimeList)
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

            }
            2 -> {

            }
        }

    }

    override fun onPageScrollStateChanged(state: Int) {

    }
}