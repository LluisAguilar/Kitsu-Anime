package com.applaudo.android.applaudoscodechallenge.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.AnimeData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_anime.view.*
import java.util.*

class ArticleRecyclerAdapter(private var mArticlesList: ArrayList<AnimeData>?,private val mResourceItemId:Int, listener: OnArticleItemClickListener):
    RecyclerView.Adapter<ArticleRecyclerAdapter.ArticleViewHolder>() {

    private var itemClickListener: OnArticleItemClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mResourceItemId, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(articleViewHolder: ArticleViewHolder, position: Int) {
        articleViewHolder.bind(mArticlesList, itemClickListener)
    }

    override fun getItemCount(): Int {
        return mArticlesList!!.size
    }

    fun updateAdapter(mArticlesList: ArrayList<AnimeData>?){
        this.mArticlesList = mArticlesList
        notifyDataSetChanged()
    }

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(articlesList: ArrayList<AnimeData>?, listener: OnArticleItemClickListener) {
            Picasso.get().load(articlesList?.get(position)?.imageUrl).into(itemView.item_article_iv)
            itemView.item_article_title_tv.text = articlesList?.get(position)?.title

            itemView.setOnClickListener {
                listener.OnArticleItemClick(adapterPosition)
            }
        }
    }

    interface OnArticleItemClickListener {
        fun OnArticleItemClick(position: Int)
    }
}