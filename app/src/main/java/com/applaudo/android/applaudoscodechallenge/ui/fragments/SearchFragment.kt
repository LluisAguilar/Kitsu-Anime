package com.applaudo.android.applaudoscodechallenge.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.SearchArticleData
import com.applaudo.android.applaudoscodechallenge.ui.activities.ArticleDetailActivity
import com.applaudo.android.applaudoscodechallenge.ui.activities.MainMenuActivity
import com.applaudo.android.applaudoscodechallenge.ui.adapters.SearchArticleRecyclerAdapter
import com.applaudo.android.applaudoscodechallenge.utils.UtilStrings
import kotlinx.android.synthetic.main.fragment_search.view.*
import java.util.ArrayList

class SearchFragment : Fragment(), SearchArticleRecyclerAdapter.OnArticleItemClickListener {


    private lateinit var mView: View
    private var mSearchedArticleAdapter = SearchArticleRecyclerAdapter(null,this)
    private var mSearchArticleList = arrayListOf<SearchArticleData>()
    private var mArticleType = UtilStrings.ANIME

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_search, container, false)

        mSearchedArticleAdapter = SearchArticleRecyclerAdapter(mSearchArticleList,this)
        mView.search_recycler_view.layoutManager = GridLayoutManager(activity as MainMenuActivity,3)
        mView.search_recycler_view.adapter = mSearchedArticleAdapter

        return mView
    }

    fun setSearchedData(searchedList: ArrayList<SearchArticleData>, artcleType:Int) {
        mArticleType = artcleType
        mSearchArticleList = searchedList
        mSearchedArticleAdapter.updateAdapter(mSearchArticleList)
        mView.search_recycler_view.adapter = mSearchedArticleAdapter
    }

    companion object {

        @JvmStatic
        fun getInstance() =
            SearchFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun OnArticleItemClick(articleId: Int) {
        val intent = Intent(activity as MainMenuActivity, ArticleDetailActivity::class.java)
        intent.putExtra("article_type", mArticleType)
        intent.putExtra("article_id",articleId.toString())
        (activity as MainMenuActivity).startActivity(intent)
    }


}