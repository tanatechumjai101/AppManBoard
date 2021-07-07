package com.example.AppManBoard.main.home.add.image

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.example.AppManBoard.R
import kotlinx.android.synthetic.main.activity_image.*

class ImageDialog(context: Context) : Dialog(context) {

    var listener: DialogListener? = null

    interface DialogListener {
        fun onCameraClick()

        fun onGalleryClick()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_image)
        setCancelable(true)

        onClickListenerCP()

    }

    private fun onClickListenerCP() {
        ib_camera.setOnClickListener {
            listener?.onCameraClick()
            dismiss()
        }
        ib_photo.setOnClickListener {

            listener?.onGalleryClick()
            dismiss()
        }
    }

}