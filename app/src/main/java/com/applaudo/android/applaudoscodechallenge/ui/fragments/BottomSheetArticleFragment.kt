package com.applaudo.android.applaudoscodechallenge.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleChapterData
import com.applaudo.android.applaudoscodechallenge.ui.activities.ArticleDetailActivity
import com.applaudo.android.applaudoscodechallenge.ui.adapters.BottomArticleRecyclerAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet_article.view.*


class BottomSheetArticleFragment(val articleList: ArrayList<ArticleChapterData>) : BottomSheetDialogFragment() {

    private lateinit var mView: View
    private lateinit var bottomArticlesAdapter: BottomArticleRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_bottom_sheet_article, container, false)

        bottomArticlesAdapter = BottomArticleRecyclerAdapter(articleList)
        mView.bottom_article_detail_recycler.layoutManager = LinearLayoutManager(activity as ArticleDetailActivity)
        mView.bottom_article_detail_recycler.adapter = bottomArticlesAdapter

        mView.bottom_fragment_dismiss_iv.setOnClickListener{
            dismiss()
        }

        return mView
    }

}