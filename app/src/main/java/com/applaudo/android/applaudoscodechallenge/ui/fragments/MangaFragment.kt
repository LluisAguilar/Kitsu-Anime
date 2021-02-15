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
import com.applaudo.android.applaudoscodechallenge.ui.activities.ArticleDetailActivity
import com.applaudo.android.applaudoscodechallenge.ui.activities.MainMenuActivity
import com.applaudo.android.applaudoscodechallenge.ui.adapters.MangaArticleRecyclerAdapter
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings
import kotlinx.android.synthetic.main.fragment_manga.view.*
import kotlinx.android.synthetic.main.fragment_manga.view.search_back_arrow
import java.util.ArrayList


class MangaFragment : Fragment(), MangaArticleRecyclerAdapter.OnArticleItemClickListener,
    SearchView.OnCloseListener, SearchView.OnQueryTextListener {

    private lateinit var mView : View

    private lateinit var mMangaArticleTrendingAdapter: MangaArticleRecyclerAdapter
    private var mTrendingArticlesList = arrayListOf<ArticleData>()

    private lateinit var mMangaArticleOnAirAdapter: MangaArticleRecyclerAdapter
    private var mOnAirArticlesList = arrayListOf<ArticleData>()

    private lateinit var mMangaArticleFinishedAdapter: MangaArticleRecyclerAdapter
    private var mFinishedArticlesList = arrayListOf<ArticleData>()

    private lateinit var mMangaArticleCategoriesAdapter: MangaArticleRecyclerAdapter
    private var mCategoriesArticlesList = arrayListOf<ArticleData>()

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
        mView = inflater.inflate(R.layout.fragment_manga, container, false)

        setSearchViewListeners()

        mMangaArticleTrendingAdapter = MangaArticleRecyclerAdapter(mTrendingArticlesList,R.layout.item_manga,this)
        mView.trending_manga_recycler.layoutManager = LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.trending_manga_recycler.adapter = mMangaArticleTrendingAdapter

        mMangaArticleOnAirAdapter = MangaArticleRecyclerAdapter(mOnAirArticlesList,R.layout.item_manga,this)
        mView.on_air_manga_recycler.layoutManager = LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.on_air_manga_recycler.adapter = mMangaArticleOnAirAdapter

        mMangaArticleFinishedAdapter = MangaArticleRecyclerAdapter(mFinishedArticlesList,R.layout.item_manga,this)
        mView.finished_manga_recycler.layoutManager = LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.finished_manga_recycler.adapter = mMangaArticleFinishedAdapter

        mMangaArticleCategoriesAdapter = MangaArticleRecyclerAdapter(mCategoriesArticlesList,R.layout.item_categories,this)
        mView.categories_manga_recycler.layoutManager = LinearLayoutManager(activity as MainMenuActivity, RecyclerView.HORIZONTAL, false)
        mView.categories_manga_recycler.adapter = mMangaArticleCategoriesAdapter

        mView.search_back_arrow.setOnClickListener {
            dismissSearchFragment()
            mView.search_back_arrow.visibility = View.GONE
            mView.manga_search_view.visibility = View.VISIBLE
            activeSearch = false
        }

        return mView
    }

    fun setSearchViewListeners() {
        mView.manga_search_view.setOnCloseListener(this)
        mView.manga_search_view.setOnQueryTextListener(this)
    }

    private fun loadSearchFragment() {
        try {
            mFragmentManager.beginTransaction()
                .add(R.id.manga_fragment_container, mSearchFragment).commit()
        } catch (isException: IllegalStateException) {
            isException.printStackTrace()
            mFragmentManager.beginTransaction()
                .add(R.id.manga_fragment_container, mSearchFragment)
                .commitAllowingStateLoss()
        }
    }

    fun dismissSearchFragment() {
        mFragmentManager.beginTransaction()
            .remove(mSearchFragment)
            .commit()
        mView.manga_fragment_container.visibility = View.GONE
        mView.manga_menu_scrollview.visibility = View.VISIBLE
    }

    fun setTrendingManga(mTrendingMangaList: ArrayList<ArticleData>) {
        mTrendingArticlesList = mTrendingMangaList
        mMangaArticleTrendingAdapter.updateAdapter(mTrendingArticlesList)
        mView.trending_manga_recycler.adapter = mMangaArticleTrendingAdapter
    }

    fun setOnAirManga(onAirArticleData: ArrayList<ArticleData>) {
        mOnAirArticlesList = onAirArticleData
        mMangaArticleOnAirAdapter.updateAdapter(mOnAirArticlesList)
        mView.on_air_manga_recycler.adapter = mMangaArticleOnAirAdapter
    }

    fun setFinishedManga(finishedArticleData: ArrayList<ArticleData>) {
        mFinishedArticlesList = finishedArticleData
        mMangaArticleFinishedAdapter.updateAdapter(mFinishedArticlesList)
        mView.finished_manga_recycler.adapter = mMangaArticleFinishedAdapter
    }

    fun setCategoryAnime(mCategoryMangaList: ArrayList<ArticleData>) {
        mFinishedArticlesList = mCategoryMangaList
        mMangaArticleCategoriesAdapter.updateAdapter(mFinishedArticlesList)
        mView.categories_manga_recycler.adapter = mMangaArticleCategoriesAdapter
    }

    companion object {

        @JvmStatic
        fun getInstance() =
            MangaFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun OnArticleItemClick(articleId:String, position: Int, resourceid:Int) {
        if (resourceid == R.layout.item_manga) {
            val intent = Intent(activity as MainMenuActivity, ArticleDetailActivity::class.java)
            intent.putExtra("article_type",UtilStrings.MANGA)
            intent.putExtra("article_id",articleId)
            (activity as MainMenuActivity).startActivity(intent)
        } else if (resourceid == R.layout.item_categories) {
            loadSearchFragment()
            mView.search_back_arrow.visibility = View.VISIBLE
            mView.manga_search_view.visibility = View.GONE
            activeSearch = true
            (activity as MainMenuActivity).getSearchMangaByCategory(UtilStrings.categoriesList.get(position))
        }

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
        if (!newText!!.isEmpty()){
            if (!mSearchFragment.isVisible) {
                loadSearchFragment()
            }
            mView.manga_fragment_container.visibility = View.VISIBLE
            mView.manga_menu_scrollview.visibility = View.GONE
            (activity as MainMenuActivity).getSearchTextManga(newText)
        }else {
            dismissSearchFragment()
        }
        return true
    }

    fun setSearchedManga(searchMangaList: ArrayList<ArticleData>) {
        val searchList = ArrayList<SearchArticleData>()
        for (x in 0 until  searchMangaList.size){
            searchList.add(SearchArticleData(searchMangaList.get(x).id, searchMangaList.get(x).title.toString(), searchMangaList.get(x).imageUrl.toString()))
        }
        mSearchFragment.setSearchedData(searchList, UtilStrings.MANGA)
    }

    fun setCategorySearched(ArticleDataList: ArrayList<ArticleData>) {
        val searchList = ArrayList<SearchArticleData>()
        mView.manga_fragment_container.visibility = View.VISIBLE
        mView.manga_menu_scrollview.visibility = View.GONE
        for (x in 0 until ArticleDataList.size) {
            searchList.add(
                SearchArticleData(
                    ArticleDataList.get(x).id,
                    ArticleDataList.get(x).title.toString(),
                    ArticleDataList.get(x).imageUrl.toString()
                )
            )
        }
        mSearchFragment.setSearchedData(searchList, UtilStrings.MANGA)
    }
}