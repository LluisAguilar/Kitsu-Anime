package com.applaudo.android.applaudoscodechallenge.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.FavoriteArticleData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_anime.view.*
import java.util.*

class FavoriteArticleRecyclerAdapter(private var mArticlesList: ArrayList<FavoriteArticleData>?, listener: OnArticleItemClickListener):
    RecyclerView.Adapter<FavoriteArticleRecyclerAdapter.ArticleViewHolder>() {

    private var itemClickListener: OnArticleItemClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(articleViewHolder: ArticleViewHolder, position: Int) {
        articleViewHolder.bind(mArticlesList, itemClickListener)
    }

    override fun getItemCount(): Int {
        return mArticlesList!!.size
    }

    fun updateAdapter(mArticlesList: ArrayList<FavoriteArticleData>?){
        this.mArticlesList = mArticlesList
        notifyDataSetChanged()
    }

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(articlesList: ArrayList<FavoriteArticleData>?, listener: OnArticleItemClickListener) {
            Picasso.get().load(articlesList?.get(position)?.imageUrl).into(itemView.item_article_iv)
            itemView.item_article_title_tv.text = articlesList?.get(position)?.title

            itemView.item_article_cv.setOnClickListener {
                listener.OnArticleItemClick(articlesList?.get(position)?.id!!.toInt(),articlesList.get(position).articleType)
            }
        }
    }

    interface OnArticleItemClickListener {
        fun OnArticleItemClick(articleId: Int, articleType:Int)
    }
}