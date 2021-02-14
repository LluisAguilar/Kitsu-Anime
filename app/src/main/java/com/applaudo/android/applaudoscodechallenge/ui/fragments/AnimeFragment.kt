package com.applaudo.android.applaudoscodechallenge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.AnimeData
import com.applaudo.android.applaudoscodechallenge.ui.activities.MainMenuActivity
import com.applaudo.android.applaudoscodechallenge.ui.adapters.ArticleRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_anime.view.*

class AnimeFragment : Fragment(), ArticleRecyclerAdapter.OnArticleItemClickListener {

    private lateinit var mView: View
    private lateinit var mArticleTrendingAdapter: ArticleRecyclerAdapter
    private var mTrendingArticlesList = arrayListOf<AnimeData>()

    private lateinit var mArticleOnAirAdapter: ArticleRecyclerAdapter
    private var mOnAirArticlesList = arrayListOf<AnimeData>()

    private lateinit var mArticleCategoryAdapter: ArticleRecyclerAdapter
    private var mCategoryArticlesList = arrayListOf<AnimeData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_anime, container, false)

        mArticleTrendingAdapter = ArticleRecyclerAdapter(mTrendingArticlesList,R.layout.item_anime,this)
        mView.trending_anime_recycler.layoutManager = LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.trending_anime_recycler.adapter = mArticleTrendingAdapter

        mArticleOnAirAdapter = ArticleRecyclerAdapter(mOnAirArticlesList,R.layout.item_anime,this)
        mView.on_air_anime_recycler.layoutManager = LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.on_air_anime_recycler.adapter = mArticleOnAirAdapter

        mArticleCategoryAdapter = ArticleRecyclerAdapter(mCategoryArticlesList,R.layout.item_categories,this)
        mView.categories_anime_recycler.layoutManager = LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.categories_anime_recycler.adapter = mArticleCategoryAdapter

        return mView
    }

    fun setTrendingAnime(mTrendingAnimeList: ArrayList<AnimeData>) {
        mTrendingArticlesList = mTrendingAnimeList
        mArticleTrendingAdapter.updateAdapter(mTrendingArticlesList)
        mView.trending_anime_recycler.adapter = mArticleTrendingAdapter
    }

    fun setOnAirAnime(mOnAirAnimeList: ArrayList<AnimeData>) {
        mOnAirArticlesList = mOnAirAnimeList
        mArticleOnAirAdapter.updateAdapter(mOnAirArticlesList)
        mView.on_air_anime_recycler.adapter = mArticleOnAirAdapter
    }

    fun setCategoryAnime(mCategoriesList: ArrayList<AnimeData>) {
        mCategoryArticlesList = mCategoriesList
        mArticleCategoryAdapter.updateAdapter(mCategoryArticlesList)
        mView.categories_anime_recycler.adapter = mArticleCategoryAdapter
    }

    companion object {

        @JvmStatic
        fun getInstance() =
            AnimeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun OnArticleItemClick(position: Int) {

    }
}