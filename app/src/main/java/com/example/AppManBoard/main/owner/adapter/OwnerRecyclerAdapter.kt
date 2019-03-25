package com.example.AppManBoard.main.owner.adapter

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.*
import com.bumptech.glide.Glide
import com.example.AppManBoard.R
import com.example.AppManBoard.model.Data
import kotlinx.android.synthetic.main.list_owner.view.*

class OwnerRecyclerAdapter(var Listdata: ArrayList<Data>?) :
        RecyclerView.Adapter<OwnerRecyclerAdapter.OwnerViewHolder>() {
    var listener: RecyclerListener? = null

    interface RecyclerListener {
        fun onItemClick(position: Int, data: Data)
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
            itemView.tv_setDetail_owner.text = Html.fromHtml(data.detail)
            itemView.tv_setTime_owner.text = data.time
            itemView.tv_setName_owner.text = data.displayname

            if (data.imageURI == "null") {
                Glide.with(itemView.context).load(R.drawable.playstore_icon).into(itemView.iv_profile_owner)
            } else {
                Glide.with(itemView.context).load(data.imageURI)
                        .into(itemView.iv_profile_owner)
            }

            itemView.setOnClickListener {
                listener?.onItemClick(position, data)
            }
        }
    }

    fun filterList(filteredCourseList: ArrayList<Data>) {
        Listdata = filteredCourseList
        notifyDataSetChanged()
    }

}