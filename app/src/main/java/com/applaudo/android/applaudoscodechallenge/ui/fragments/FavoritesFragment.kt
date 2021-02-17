package com.applaudo.android.applaudoscodechallenge.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.FavoriteArticleData
import com.applaudo.android.applaudoscodechallenge.ui.activities.ArticleDetailActivity
import com.applaudo.android.applaudoscodechallenge.ui.activities.MainMenuActivity
import com.applaudo.android.applaudoscodechallenge.ui.adapters.FavoriteArticleRecyclerAdapter
import com.applaudo.android.applaudoscodechallenge.ui.utils.UtilStrings
import kotlinx.android.synthetic.main.fragment_favorites.view.*


class FavoritesFragment : Fragment(), FavoriteArticleRecyclerAdapter.OnArticleItemClickListener {

    private lateinit var mView: View
    private var mFavoritesList = arrayListOf<FavoriteArticleData>()
    private lateinit var favoriteArticleAdapter: FavoriteArticleRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_favorites, container, false)

        return mView
    }

    fun setFavoriteArticlesInView(favoriteArticles: ArrayList<FavoriteArticleData>) {
        mFavoritesList = favoriteArticles

        if (mFavoritesList.size > 0) {
            favoriteArticleAdapter = FavoriteArticleRecyclerAdapter(mFavoritesList, this)
            mView.favorite_recycler_view.layoutManager =
                GridLayoutManager(activity as MainMenuActivity, 3)
            mView.favorite_recycler_view.adapter = favoriteArticleAdapter

            mView.favorites_empty_message_layout.visibility = View.GONE
            mView.favorites_layout.visibility = View.VISIBLE
        } else {
            mView.favorites_empty_message_layout.visibility = View.VISIBLE
            mView.favorites_layout.visibility = View.GONE
        }
    }

    companion object {

        @JvmStatic
        fun getInstance() =
            FavoritesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun OnArticleItemClick(articleId: Int, articleType:Int) {
        val intent = Intent(activity as MainMenuActivity, ArticleDetailActivity::class.java)

        if (articleType == UtilStrings.ANIME){
            intent.putExtra("article_type", UtilStrings.ANIME)
        }else {
            intent.putExtra("article_type", UtilStrings.MANGA)
        }
        intent.putExtra("article_id",articleId.toString())
        (activity as MainMenuActivity).startActivity(intent)
    }
}