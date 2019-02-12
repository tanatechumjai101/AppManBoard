package com.example.thefirstnewprojectaddtoday28jan62.main.owner.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.*
import com.bumptech.glide.Glide
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import kotlinx.android.synthetic.main.list_owner.view.*

class OwnerRecyclerAdapter(var Listdata: ArrayList<Data>) : RecyclerView.Adapter<OwnerRecyclerAdapter.OwnerViewHolder>() {
    lateinit var mCotext: Context
    lateinit var mGestureDetector: GestureDetector

    internal fun OnTouchAction() {
        fun edit(subject: String, detail: String) {}
        fun delete(index: Int) {}
    }

    override fun onCreateViewHolder(view: ViewGroup, position: Int): OwnerViewHolder {
        val view = LayoutInflater.from(view.context).inflate(R.layout.list_owner, view, false)
        return OwnerViewHolder(view)

    }

    override fun getItemCount(): Int {
        return Listdata?.size
    }

    override fun onBindViewHolder(view: OwnerViewHolder, position: Int) {
        val data: Data = Listdata[position]
        view.itemView.tv_setSubject_owner.text = data.subject
        view.itemView.tv_setDetail_owner.text = data.detail
        view.itemView.tv_setTime_owner.text = data.time
        Glide.with(view.itemView.context).load(data.imageURI).into(view.itemView.iv_profile_owner)
        view.itemView.tv_setName_owner.text = data.displayname

    }


    class OwnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnTouchListener, GestureDetector.OnGestureListener {

        lateinit var mGestureDetector: GestureDetector

        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
           val action =  mGestureDetector.onTouchEvent(event)
//            when(action){
//
//            }

            return true


        }

        override fun onShowPress(e: MotionEvent?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onDown(e: MotionEvent?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onLongPress(e: MotionEvent?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}