package com.applaudo.android.applaudoscodechallenge.ui.adapters
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.ui.fragments.AnimeFragment
import com.applaudo.android.applaudoscodechallenge.ui.fragments.FavoritesFragment
import com.applaudo.android.applaudoscodechallenge.ui.fragments.MangaFragment
import java.util.*

class MenuPagerAdapter(manager: FragmentManager, private val mContext: Context) : FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

    override fun getItem(position: Int): Fragment {
        val animeFragment = AnimeFragment.getInstance()
        val mangaFragment = MangaFragment.getInstance()
        val favoritesFragment = FavoritesFragment.getInstance()
        var fragment = Fragment()
        if (position == 0) {
            fragment = animeFragment
        } else if (position == 1) {
            fragment = mangaFragment
        }else if (position == 2){
            fragment = favoritesFragment
        }
        return fragment
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        var tabTittle = mContext.getString(R.string.anime)
        if (position == 0){
            tabTittle = mContext.getString(R.string.anime)
        }else if (position == 1) {
            tabTittle = mContext.getString(R.string.manga)
        }else if (position == 2){
            tabTittle = mContext.getString(R.string.favorites)
        }
        return tabTittle.capitalize(Locale.ROOT)
    }


}