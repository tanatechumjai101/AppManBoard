package com.example.thefirstnewprojectaddtoday28jan62.main.home.add.image

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Window
import com.example.thefirstnewprojectaddtoday28jan62.R
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity(context: Context) : Dialog(context) {

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