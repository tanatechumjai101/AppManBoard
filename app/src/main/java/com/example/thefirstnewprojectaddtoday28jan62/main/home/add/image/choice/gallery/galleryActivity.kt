package com.example.thefirstnewprojectaddtoday28jan62.main.home.add.image.choice.gallery

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_gallery.*
import java.io.IOException
import java.util.*
import java.io.InputStream
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File


class galleryActivity : AppCompatActivity() {

        internal var storage: FirebaseStorage? = null
        internal var storageReference: StorageReference? = null
        private val PERMISSION_CODE = 1001
        private val IMAGE_PICK_CODE = 1000
        lateinit var file_Path: Uri
        lateinit var uri_image: String
        lateinit var mActivity: Activity
        lateinit var file: File

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        mActivity = galleryActivity()
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        progressDialog = ProgressDialog(this)

        progressBar_Gallry.visibility = View.VISIBLE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, PERMISSION_CODE)
            } else {
                pickImageFromGallery()
            }
        } else {
            pickImageFromGallery()
        }


    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        progressDialog.show()
        startActivityForResult(intent, IMAGE_PICK_CODE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_CODE -> {
                progressBar_Gallry.visibility = View.INVISIBLE
                if ((grantResults.size) > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun uploadImage() {

        if (file_Path != null) {

            progressDialog.setTitle("Uploading....")

            val imageRef = storageReference!!.child("Gallery/" + UUID.randomUUID().toString())
            imageRef.putFile(file_Path)
                .addOnSuccessListener {

                    Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                    finish()

                }
                .addOnProgressListener { taskSnapShot ->
                    val progress = 100.0 * taskSnapShot.bytesTransferred / taskSnapShot.totalByteCount
                    progressDialog.setMessage("Uploaded" + progress.toInt() + "%...")
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        progressBar_Gallry.visibility = View.INVISIBLE

        when (requestCode) {
            IMAGE_PICK_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    file_Path = data?.data!!
                    uploadImage()

                    finish()
                } else {
                    finish()
                }
            }
        }
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE && data != null && data.data != null) {
//


//            try {
//                val bitmaps = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
//
//                val bytes = ByteArrayOutputStream()
//                bitmaps.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//                val path = MediaStore.Images.Media.insertImage(contentResolver, bitmaps, "Title", null)
//
//                val getbitmap = assetsToBitmap(path)
//
//                val myDir =  File(cacheDir, "folder")
//                myDir.mkdir()
//                myDir.writeText("$getbitmap")
//
//
//                if (getbitmap != null) {
//                    val resizedBitmap = getbitmap.scale(500)
//
//                    toast("Bitmap resized.")
//
//
//                } else {
//                    toast("bitmap not found.")
//                }
//
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//            finish()
//        } else {
//            finish()
//        }
    }

    private fun assetsToBitmap(fileName: String): Bitmap? {
        return try {
            val stream = assets.open(fileName)
            BitmapFactory.decodeStream(stream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun Bitmap.scale(maxWidthAndHeight: Int): Bitmap {
        var newWidth = 0
        var newHeight = 0

        if (this.width >= this.height) {
            val ratio: Float = this.width.toFloat() / this.height.toFloat()

            newWidth = maxWidthAndHeight
            // Calculate the new height for the scaled bitmap
            newHeight = Math.round(maxWidthAndHeight / ratio)
        } else {
            val ratio: Float = this.height.toFloat() / this.width.toFloat()

            // Calculate the new width for the scaled bitmap
            newWidth = Math.round(maxWidthAndHeight / ratio)
            newHeight = maxWidthAndHeight
        }

        return Bitmap.createScaledBitmap(
            this,
            newWidth,
            newHeight,
            false
        )
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finishAffinity()
    }

}
