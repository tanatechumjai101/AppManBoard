package com.example.thefirstnewprojectaddtoday28jan62.main.owner.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.*
import com.bumptech.glide.Glide
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import kotlinx.android.synthetic.main.list_owner.view.*

class OwnerRecyclerAdapter(var Listdata: MutableList<Data>?) :
    RecyclerView.Adapter<OwnerRecyclerAdapter.OwnerViewHolder>() {
    lateinit var mCotext: Context
    var listener: RecyclerListener? = null

    interface RecyclerListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(view: ViewGroup, position: Int): OwnerViewHolder {
        val rootView = LayoutInflater.from(view.context).inflate(R.layout.list_owner, view, false)
        return OwnerViewHolder(rootView)

    }

    override fun getItemCount(): Int {
        return Listdata?.size ?: 0
    }

    override fun onBindViewHolder(holder: OwnerViewHolder, position: Int) {
        val data: Data = Listdata!![position]
        holder.setResource(data, listener, position)
    }

    class OwnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun setResource(
            data: Data,
            listener: RecyclerListener?,
            position: Int
        ) {
            itemView.tv_setSubject_owner.text = data.subject
            itemView.tv_setDetail_owner.text = data.detail
            itemView.tv_setTime_owner.text = data.time
            Glide.with(itemView.context).load(data.imageURI).into(itemView.iv_profile_owner)
            itemView.tv_setName_owner.text = data.displayname
            itemView.setOnClickListener {
                listener?.onItemClick(position)
            }
        }
    }

}