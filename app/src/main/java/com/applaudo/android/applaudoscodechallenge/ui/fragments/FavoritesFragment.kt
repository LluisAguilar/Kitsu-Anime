package com.applaudo.android.applaudoscodechallenge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.applaudo.android.applaudoscodechallenge.R


class FavoritesFragment : Fragment() {

    private lateinit var mView : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorites, container, false)
        return mView
    }

    companion object {

        @JvmStatic
        fun getInstance() =
            FavoritesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}