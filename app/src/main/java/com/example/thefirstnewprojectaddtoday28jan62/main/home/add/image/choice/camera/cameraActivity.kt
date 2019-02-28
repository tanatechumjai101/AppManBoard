package com.example.thefirstnewprojectaddtoday28jan62.main.home.add.image.choice.camera

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.main.home.add.image.choice.gallery.galleryActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_camera.*
import java.util.*

class cameraActivity : AppCompatActivity() {

        private val PERMISSION_CODE = 1000
        lateinit var image_uri: Uri
        private val IMAGE_CAPTURE_CODE = 1001
        internal var storage: FirebaseStorage? = null
        internal var storageReference: StorageReference? = null
        private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        progressDialog = ProgressDialog(this)
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        progressBar_Camera.visibility = View.VISIBLE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED
            ) {
                val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                openCamera()
            }
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        val values = ContentValues()
        progressDialog.show()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")




        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)

        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        progressBar_Camera.visibility = View.INVISIBLE
        when (requestCode) {
            PERMISSION_CODE -> {

                if ((grantResults.size) > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

    private fun uploadImage() {

        if (image_uri != null) {
            progressDialog.setTitle("Uploading....")
            val imageRef = storageReference!!.child("Camera/" + UUID.randomUUID().toString())
            imageRef.putFile(image_uri)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Image Uploaded", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnProgressListener { taskSnapShot ->
                    val progress = 100.0 * taskSnapShot.bytesTransferred / taskSnapShot.totalByteCount
                    progressDialog.setMessage("Uploaded" + progress.toInt() + "%...")
                }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        progressBar_Camera.visibility = View.INVISIBLE

        when (requestCode) {
            IMAGE_CAPTURE_CODE -> {
                if (resultCode == Activity.RESULT_OK) {

                    Log.d("TAG",image_uri.toString())
                    uploadImage()
                    progressDialog.show()
                    finish()
                } else {
                    finish()
                }
            }
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finishAffinity()
    }
}
