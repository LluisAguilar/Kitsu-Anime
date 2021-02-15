package com.applaudo.android.applaudoscodechallenge.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.SearchArticleData
import com.applaudo.android.applaudoscodechallenge.domain.models.StreamerData
import com.applaudo.android.applaudoscodechallenge.ui.activities.ArticleDetailActivity
import com.applaudo.android.applaudoscodechallenge.ui.activities.MainMenuActivity
import com.applaudo.android.applaudoscodechallenge.ui.adapters.AnimeArticleRecyclerAdapter
import com.applaudo.android.applaudoscodechallenge.ui.adapters.StreamerItemAdapter
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings
import kotlinx.android.synthetic.main.fragment_anime.view.*

class AnimeFragment : Fragment(), AnimeArticleRecyclerAdapter.OnArticleItemClickListener,
    StreamerItemAdapter.OnStreamerItemClickListener,
    SearchView.OnCloseListener, SearchView.OnQueryTextListener {

    private lateinit var mView: View

    private lateinit var mAnimeArticleTrendingAdapter: AnimeArticleRecyclerAdapter
    private var mTrendingArticlesList = arrayListOf<ArticleData>()

    private lateinit var mAnimeArticleOnAirAdapter: AnimeArticleRecyclerAdapter
    private var mOnAirArticlesList = arrayListOf<ArticleData>()

    private lateinit var mAnimeArticleCategoryAdapter: AnimeArticleRecyclerAdapter
    private var mCategoryArticlesList = arrayListOf<ArticleData>()

    private lateinit var mStreamerItemAdapter: StreamerItemAdapter
    private var mStreamersList = arrayListOf<StreamerData>()

    private lateinit var mFragmentManager: FragmentManager
    private lateinit var mSearchFragment: SearchFragment

    private var activeSearch = false

    override fun onCreate(savedInstanceState: Bundle?) {
        mFragmentManager = childFragmentManager
        mSearchFragment = SearchFragment.getInstance()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_anime, container, false)

        setSearchViewListeners()

        mAnimeArticleTrendingAdapter =
            AnimeArticleRecyclerAdapter(mTrendingArticlesList, R.layout.item_anime, this)
        mView.trending_anime_recycler.layoutManager =
            LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.trending_anime_recycler.adapter = mAnimeArticleTrendingAdapter

        mAnimeArticleOnAirAdapter =
            AnimeArticleRecyclerAdapter(mOnAirArticlesList, R.layout.item_anime, this)
        mView.on_air_anime_recycler.layoutManager =
            LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.on_air_anime_recycler.adapter = mAnimeArticleOnAirAdapter

        mAnimeArticleCategoryAdapter =
            AnimeArticleRecyclerAdapter(mCategoryArticlesList, R.layout.item_categories, this)
        mView.categories_anime_recycler.layoutManager =
            LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.categories_anime_recycler.adapter = mAnimeArticleCategoryAdapter

        mStreamerItemAdapter = StreamerItemAdapter(mStreamersList, this)
        mView.streamers_anime_recycler.layoutManager =
            LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.streamers_anime_recycler.adapter = mStreamerItemAdapter

        mView.search_back_arrow.setOnClickListener {
            dismissSearchFragment()
            mView.search_back_arrow.visibility = View.GONE
            mView.anime_search_view.visibility = View.VISIBLE
            activeSearch = false
        }

        if (activeSearch) {
            mView.search_back_arrow.visibility = View.VISIBLE
            mView.anime_search_view.visibility = View.GONE
            mView.anime_fragment_container.visibility = View.VISIBLE
            mView.anime_menu_scrollview.visibility = View.GONE
        }

        return mView
    }

    fun setSearchViewListeners() {
        mView.anime_search_view.setOnCloseListener(this)
        mView.anime_search_view.setOnQueryTextListener(this)
    }

    fun setTrendingAnime(mTrendingAnimeList: ArrayList<ArticleData>) {
        mTrendingArticlesList = mTrendingAnimeList
        mAnimeArticleTrendingAdapter.updateAdapter(mTrendingArticlesList)
        mView.trending_anime_recycler.adapter = mAnimeArticleTrendingAdapter
    }

    fun setOnAirAnime(mOnAirAnimeList: ArrayList<ArticleData>) {
        mOnAirArticlesList = mOnAirAnimeList
        mAnimeArticleOnAirAdapter.updateAdapter(mOnAirArticlesList)
        mView.on_air_anime_recycler.adapter = mAnimeArticleOnAirAdapter
    }

    fun setCategoryAnime(mCategoriesList: ArrayList<ArticleData>) {
        mCategoryArticlesList = mCategoriesList
        mAnimeArticleCategoryAdapter.updateAdapter(mCategoryArticlesList)
        mView.categories_anime_recycler.adapter = mAnimeArticleCategoryAdapter
    }

    fun setStreamersList(streamersList: ArrayList<StreamerData>) {
        mStreamersList = streamersList
        mStreamerItemAdapter.updateAdapter(mStreamersList)
        mView.streamers_anime_recycler.adapter = mStreamerItemAdapter
    }

    companion object {

        @JvmStatic
        fun getInstance() =
            AnimeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    private fun loadSearchFragment() {
        try {
            mFragmentManager.beginTransaction()
                .add(R.id.anime_fragment_container, mSearchFragment).commit()
        } catch (isException: IllegalStateException) {
            isException.printStackTrace()
            mFragmentManager.beginTransaction()
                .add(R.id.anime_fragment_container, mSearchFragment)
                .commitAllowingStateLoss()
        }
    }

    fun dismissSearchFragment() {
        mFragmentManager.beginTransaction()
            .remove(mSearchFragment)
            .commit()
        mView.anime_fragment_container.visibility = View.GONE
        mView.anime_menu_scrollview.visibility = View.VISIBLE
    }


    override fun OnArticleItemClick(articleId:String, position: Int, resourceid: Int) {

        if (resourceid == R.layout.item_anime) {
            val intent = Intent(activity as MainMenuActivity, ArticleDetailActivity::class.java)
            intent.putExtra("article_type",UtilStrings.ANIME)
            intent.putExtra("article_id",articleId)
            (activity as MainMenuActivity).startActivity(intent)
        } else if (resourceid == R.layout.item_categories) {
            loadSearchFragment()
            mView.search_back_arrow.visibility = View.VISIBLE
            mView.anime_search_view.visibility = View.GONE
            activeSearch = true
            (activity as MainMenuActivity).getSearchAnimeByCategory(
                UtilStrings.categoriesList.get(
                    position
                )
            )
        }

    }

    override fun OnStreamerItemClick(streamer:String, position: Int) {
        loadSearchFragment()
        mView.search_back_arrow.visibility = View.VISIBLE
        mView.anime_search_view.visibility = View.GONE
        activeSearch = true
        (activity as MainMenuActivity).getSearchAnimeByStreamer(streamer)
    }

    override fun onClose(): Boolean {
        if (mSearchFragment.isVisible) {
            dismissSearchFragment()
        }

        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText!!.isEmpty()) {
            if (!mSearchFragment.isVisible) {
                loadSearchFragment()
            }
            mView.anime_fragment_container.visibility = View.VISIBLE
            mView.anime_menu_scrollview.visibility = View.GONE
            (activity as MainMenuActivity).getSearchTextAnime(newText)
        } else {
            if (!activeSearch) {
                dismissSearchFragment()
            }

        }
        return true
    }

    fun setSearchedAnime(searchAnimeList: ArrayList<ArticleData>) {
        val searchList = ArrayList<SearchArticleData>()
        for (x in 0 until searchAnimeList.size) {
            searchList.add(
                SearchArticleData(
                    searchAnimeList.get(x).id,
                    searchAnimeList.get(x).title.toString(),
                    searchAnimeList.get(x).imageUrl.toString()
                )
            )
        }
        mSearchFragment.setSearchedData(searchList, UtilStrings.ANIME)
    }

    fun setCategorySearched(ArticleDataList: java.util.ArrayList<ArticleData>) {
        val searchList = ArrayList<SearchArticleData>()
        mView.anime_fragment_container.visibility = View.VISIBLE
        mView.anime_menu_scrollview.visibility = View.GONE
        for (x in 0 until ArticleDataList.size) {
            searchList.add(
                SearchArticleData(
                    ArticleDataList.get(x).id,
                    ArticleDataList.get(x).title.toString(),
                    ArticleDataList.get(x).imageUrl.toString()
                )
            )
        }
        mSearchFragment.setSearchedData(searchList, UtilStrings.ANIME)
    }
}