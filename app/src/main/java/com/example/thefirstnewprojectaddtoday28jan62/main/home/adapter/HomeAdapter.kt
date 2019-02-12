package com.example.thefirstnewprojectaddtoday28jan62.main.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import com.example.thefirstnewprojectaddtoday28jan62.R
import kotlinx.android.synthetic.main.list_data.view.*

class HomeAdapter(var Listdata: ArrayList<Data>?) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    lateinit var mContext: Context

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.list_data, p0, false)
        mContext = p0.context
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return Listdata?.size ?: 0
    }

    override fun onBindViewHolder(p0: ViewHolder, position: Int) {
        val data: Data = Listdata?.get(position)!!
        p0.itemView.tv_setSubject.text = data.subject
        p0.itemView.tv_setDetail.text = data.detail
        p0.itemView.tv_setTime.text = data.time
        p0.itemView.tv_setName.text = data.displayname
        Glide.with(p0.itemView.context).load(data.imageURI)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .into(p0.itemView.iv_profile)
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

    }

}