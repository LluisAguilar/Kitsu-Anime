package com.applaudo.android.applaudoscodechallenge.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_anime.view.*
import java.util.*

class MangaArticleRecyclerAdapter(private var mArticlesList: ArrayList<ArticleData>?, private val mResourceItemId:Int, listener: OnArticleItemClickListener):
    RecyclerView.Adapter<MangaArticleRecyclerAdapter.ArticleViewHolder>() {

    private var itemClickListener: OnArticleItemClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mResourceItemId, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(articleViewHolder: ArticleViewHolder, position: Int) {
        articleViewHolder.bind(mArticlesList, mResourceItemId, itemClickListener)
    }

    override fun getItemCount(): Int {
        return mArticlesList!!.size
    }

    fun updateAdapter(mArticlesList: ArrayList<ArticleData>?){
        this.mArticlesList = mArticlesList
        notifyDataSetChanged()
    }

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(articlesList: ArrayList<ArticleData>?,resourceid:Int, listener: OnArticleItemClickListener) {
            Picasso.get().load(articlesList?.get(position)?.imageUrl).into(itemView.item_article_iv)
            itemView.item_article_title_tv.text = articlesList?.get(position)?.title

            itemView.item_article_cv.setOnClickListener {
                listener.OnArticleItemClick(articlesList?.get(position)?.id!!,adapterPosition, resourceid)
            }
        }
    }

    interface OnArticleItemClickListener {
        fun OnArticleItemClick(articleId:String, position: Int, resourceId:Int)
    }
}