package com.example.AppManBoard.main.home

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import com.bumptech.glide.Glide
import com.example.AppManBoard.R
import com.example.AppManBoard.model.Data
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_selete.*

class ContentDialogFragment : DialogFragment() {

    private var data = ""
    private lateinit var mActivity: Activity

    companion object {
        fun newInstance(data: String): ContentDialogFragment {
            return ContentDialogFragment().apply {
                this.arguments = Bundle().apply {
                    this.putString("data", data)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = activity!!
        arguments?.let {
            data = it.getString("data", "")
        }
        val root = inflater.inflate(R.layout.dialog_selete, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataModel = Gson().fromJson(data, Data::class.java)

        tv_subject_showAlert.text = dataModel.subject
        ib_close.setOnClickListener {
            dismissAllowingStateLoss()
        }
        if (dataModel.imageURI == "null" || dataModel.imageURI.isNullOrEmpty() || dataModel.imageURI == null) {
                    Glide.with(mActivity).load(R.drawable.playstore_icon).into(iv_profile_showAlert)
                } else {
                    Glide.with(mActivity).load(dataModel.imageURI).into(iv_profile_showAlert)
                }
//        Load Data from html string

        val body = "<!DOCTYPE html><html><body>"
        val content = body + dataModel.detail + "</body></html>"
        webView.webChromeClient = WebChromeClient()
        webView.loadDataWithBaseURL(null, content, "text/html; charset=utf-8", "UTF-8", "about:blank")
    }
}