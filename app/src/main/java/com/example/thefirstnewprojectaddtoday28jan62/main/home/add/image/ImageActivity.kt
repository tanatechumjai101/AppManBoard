package com.example.thefirstnewprojectaddtoday28jan62.main.home.add.image

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.Window
import com.example.thefirstnewprojectaddtoday28jan62.R
import kotlinx.android.synthetic.main.activity_image.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.FileProvider
import android.widget.Toast
import com.example.thefirstnewprojectaddtoday28jan62.main.home.add.image.choice.camera.cameraActivity
import com.example.thefirstnewprojectaddtoday28jan62.main.home.add.image.choice.gallery.galleryActivity
import java.io.IOException




class ImageActivity(context: Context) : Dialog(context) {

//    var currentPath: String? = null
//    val TAKE_PICTURE = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_image)
        setCancelable(true)

        ib_camera.setOnClickListener {
            Toast.makeText(context,"Camera", Toast.LENGTH_SHORT).show()
            val intent = Intent(context,cameraActivity::class.java)
            context.startActivity(intent)
        }
        ib_photo.setOnClickListener {
            Toast.makeText(context,"Photo",Toast.LENGTH_SHORT).show()
            val intent = Intent(context,galleryActivity::class.java)
            context.startActivity(intent)
        }
    }

//    private fun dispatchCameraIntent(){
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        mActivity.startActivityForResult(intent,TAKE_PICTURE)
//        val pm = this.context.packageManager
//        if(intent.resolveActivity(pm) != null){
//            var photoFile: File? = null
//            try{
//                photoFile = createImage()
//            }catch (e: IOException){
//                e.printStackTrace()
//            }
//            if(photoFile != null){
//                var photoUrl = FileProvider.getUriForFile(mActivity,"com.consuming.application.fileprovider",photoFile)
//                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUrl)
//                mActivity.startActivityForResult(intent,TAKE_PICTURE)
//            }
//        }
    }
//    private fun createImage(): File {
//        val timeStamp = SimpleDateFormat("d-MMM-yyyy-HH:mm:ss").format(Date())
//        val imageName = "JPEG_"+timeStamp+"_"
//        var storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//        var image = File.createTempFile(imageName,".jpg",storageDir)
//
//        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        val imageFileName = "JPEG_" + timeStamp + "_"
//        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
//
//        currentPath = image.absolutePath
//        return image
//    }



//}
//Environment.DIRECTORY_PICTURES