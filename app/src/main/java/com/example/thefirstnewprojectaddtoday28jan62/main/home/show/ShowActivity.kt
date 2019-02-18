package com.example.thefirstnewprojectaddtoday28jan62.main.home.show

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.main.home.adapter.HomeAdapter
import com.example.thefirstnewprojectaddtoday28jan62.model.Data

class ShowActivity : AppCompatActivity() {
//    lateinit var tv_subject: TextView
//    lateinit var tv_detail: TextView
//    lateinit var iv_profile: ImageView
    lateinit var iv_close: ImageView
//    lateinit var mActivity: Activity
//    var adapter: HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

//        adapter!!.listener = object : HomeAdapter.RecyclerListener_pageHome {
//
//            override fun onItemClick(position: Int, data: Data) {
//
                val inflater = LayoutInflater.from(this)
                val view = inflater.inflate(R.layout.activity_show, null)
//
//                tv_subject = view.findViewById(R.id.tv_subject_showAlert)
//                tv_detail = view.findViewById(R.id.tv_detail_showAlert)
//                iv_profile = view.findViewById(R.id.iv_profile_showAlert)
//                iv_close = view.findViewById(R.id.iv_closeShow)
//                iv_close.setOnClickListener {
//                    finish()
//                }
////
//                tv_subject.text = data.subject
//                tv_detail.text = data.detail
//                Glide.with(mActivity).load(data.imageURI).into(iv_profile)
//                iv_close.setOnClickListener {
//                        finish()
//                }
//            }
//        }

    }
}
