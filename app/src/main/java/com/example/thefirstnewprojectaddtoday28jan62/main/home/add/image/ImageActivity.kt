package com.example.thefirstnewprojectaddtoday28jan62.main.home.add.image

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.example.thefirstnewprojectaddtoday28jan62.R
import kotlinx.android.synthetic.main.activity_image.*

class ImageActivity(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_image)
        setCancelable(true)

        ib_camera.setOnClickListener {
            Toast.makeText(context,"Camera",Toast.LENGTH_SHORT).show()
        }
        ib_photo.setOnClickListener {
            Toast.makeText(context,"Photo",Toast.LENGTH_SHORT).show()
        }



    }
}
