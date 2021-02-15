package com.applaudo.android.applaudoscodechallenge.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.applaudo.android.applaudoscodechallenge.R
import com.applaudo.android.applaudoscodechallenge.domain.models.StreamerData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_anime.view.*
import kotlinx.android.synthetic.main.item_streamers.view.*
import java.util.*

class StreamerItemAdapter(private var mStreamersList: ArrayList<StreamerData>?, listener: OnStreamerItemClickListener):
    RecyclerView.Adapter<StreamerItemAdapter.StreamerViewHolder>() {

    private var itemClickListener: OnStreamerItemClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): StreamerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_streamers, parent, false)
        return StreamerViewHolder(view)
    }

    override fun onBindViewHolder(streamerViewHolder: StreamerViewHolder, position: Int) {
        streamerViewHolder.bind(mStreamersList, itemClickListener)
    }

    override fun getItemCount(): Int {
        return mStreamersList!!.size
    }

    fun updateAdapter(mStreamersList: ArrayList<StreamerData>?){
        this.mStreamersList = mStreamersList
        notifyDataSetChanged()
    }

    class StreamerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(streamersList: ArrayList<StreamerData>?, listener: OnStreamerItemClickListener) {
            Picasso.get().load(streamersList?.get(position)?.imageUrl).into(itemView.item_streamer_iv)

            itemView.item_streamer_cv.setOnClickListener {
                listener.OnStreamerItemClick(streamersList?.get(position)?.name!!,adapterPosition)
            }
        }
    }

    interface OnStreamerItemClickListener {
        fun OnStreamerItemClick(streamer:String, position: Int)
    }
}