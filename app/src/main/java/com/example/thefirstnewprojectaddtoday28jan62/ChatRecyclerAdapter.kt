package com.example.thefirstnewprojectaddtoday28jan62

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ChatRecyclerAdapter : RecyclerView.Adapter<ChatRecyclerAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ChatViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.list_data, p0, false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int = 0

    override fun onBindViewHolder(p0: ChatViewHolder, p1: Int) {

    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}