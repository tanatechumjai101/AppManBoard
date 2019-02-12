package com.example.thefirstnewprojectaddtoday28jan62.main.owner.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import kotlinx.android.synthetic.main.list_data.view.*

class OwnerRecyclerAdapter(var Listdata: ArrayList<Data>) : RecyclerView.Adapter<OwnerRecyclerAdapter.ChatViewHolder>() {
    lateinit var mContext: Context
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ChatViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_data, p0, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int{
            return Listdata.size
    }

    override fun onBindViewHolder(view: ChatViewHolder, position: Int) {
        val data: Data = Listdata[position]
        view.itemView.tv_setSubject.text = data.subject
        view.itemView.tv_setDetail.text = data.detail
        view.itemView.tv_setTime.text = data.time
        Glide.with(view.itemView.context).load(data.imageURI).into(view.itemView.iv_profile)
        view.itemView.tv_setName.text = data.displayname
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}