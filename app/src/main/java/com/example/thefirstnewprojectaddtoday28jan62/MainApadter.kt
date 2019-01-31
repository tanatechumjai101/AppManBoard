package com.example.thefirstnewprojectaddtoday28jan62

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_data.view.*

class MainApadter(val Listdata: ArrayList<Data>) : RecyclerView.Adapter<MainApadter.ViewHolder>() {


    lateinit var mContext: Context
    interface onEditInterface {
//        fun onEditNote(Index: String, DataText:String)
//        fun onDeleteNote(Index: Int)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.list_data, p0, false)
        mContext = p0.context
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return Listdata.size
    }

    override fun onBindViewHolder(p0: ViewHolder, position: Int) {
        val data: Data = Listdata[position]
        p0.itemView.text_Index_page_adapter.text = data.Index
        p0.itemView.text_Data_page_adapter.text = data.data
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

    }

}