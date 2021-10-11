package com.applaudo.android.applaudoscodechallenge.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MenuPagerAdapter(val fragmentList: List<Fragment>, fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()

        when(position){
             0-> {
                fragment =  fragmentList.get(0)
             }

            1-> {
                fragment =  fragmentList.get(1)
            }

            2-> {
                fragment =  fragmentList.get(2)
            }
        }

        return fragment
    }


}