package com.applaudo.android.applaudoscodechallenge.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.ui.adapters.MenuPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainMenuActivity : AppCompatActivity() {

    private lateinit var mMenuPagerAdapter : MenuPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mMenuPagerAdapter = MenuPagerAdapter(supportFragmentManager, this)
        menu_viewpager.adapter = mMenuPagerAdapter
        myflights_tabs.setupWithViewPager(menu_viewpager)
    }
}