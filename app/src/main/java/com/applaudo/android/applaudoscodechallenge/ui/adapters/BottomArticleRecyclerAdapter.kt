package com.applaudo.android.applaudoscodechallenge.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleChapterData
import com.applaudo.android.applaudoscodechallenge.domain.models.ArticleData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_anime.view.*
import kotlinx.android.synthetic.main.item_anime.view.item_article_title_tv
import kotlinx.android.synthetic.main.item_chapters_characters.view.*
import java.util.*

class BottomArticleRecyclerAdapter(private var mArticlesList: ArrayList<ArticleChapterData>?):
    RecyclerView.Adapter<BottomArticleRecyclerAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chapters_characters, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(articleViewHolder: ArticleViewHolder, position: Int) {
        articleViewHolder.bind(mArticlesList)
    }

    override fun getItemCount(): Int {
        return mArticlesList!!.size
    }

    class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(articlesList: ArrayList<ArticleChapterData>?) {
            itemView.item_number.text = articlesList?.get(position)?.itemNumber
            itemView.item_title.text = articlesList?.get(position)?.itemTitle
        }
    }

}