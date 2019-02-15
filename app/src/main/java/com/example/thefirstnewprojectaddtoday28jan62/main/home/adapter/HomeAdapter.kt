package com.example.thefirstnewprojectaddtoday28jan62.main.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import com.example.thefirstnewprojectaddtoday28jan62.R
import kotlinx.android.synthetic.main.list_data.view.*

class HomeAdapter(var Listdata: ArrayList<Data>?) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    var listener: HomeAdapter.RecyclerListener_pageHome? = null
    lateinit var mContext: Context

    interface RecyclerListener_pageHome {
        fun onItemClick(position: Int, data: Data)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.list_data, p0, false)
        mContext = p0.context
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return Listdata?.size ?: 0
    }

    override fun onBindViewHolder(p0: ViewHolder, position: Int) {
        val data: Data = Listdata!![position]
        p0.setResource(data, listener, position)
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
            fun setResource(data: Data, listener: RecyclerListener_pageHome?, position: Int){
                itemView.tv_setSubject.text = data.subject
                itemView.tv_setDetail.text = data.detail
                itemView.tv_setTime.text = data.time
                itemView.tv_setName.text = data.displayname
                Glide.with(itemView.context).load(data.imageURI)
                    .into(itemView.iv_profile)
                itemView.setOnClickListener {
                    listener?.onItemClick(position, data)
                }
        }
    }
}