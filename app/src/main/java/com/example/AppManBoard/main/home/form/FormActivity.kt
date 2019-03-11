package com.example.AppManBoard.main.home.form

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import com.example.AppManBoard.R
import android.view.inputmethod.InputMethodManager
import com.example.AppManBoard.main.home.add.image.ImageDialog
import com.example.AppManBoard.model.Data
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.toolbar_form_activity.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.os.Environment
import android.os.StrictMode
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.AppManBoard.toByteArray
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class FormActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog

    private var fileName: String = ""
    private val REQUEST_PERMISSION_CAMERA = 579
    private val REQUEST_PERMISSION_GALLERY = 975
    var nPreview = ""
    var PreviewHtml = ""
    private val PICK_CAMARA = 1234
    private val PICK_GALLARY = 4321
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    private lateinit var imagesFolder: File
    private var imageSavedPath: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        progressDialog = ProgressDialog(this)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        imagesFolder = File(Environment.getExternalStorageDirectory(), "AppmanBoard")

        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)


        webViewFormPage.setEditorHeight(200)
        webViewFormPage.setEditorFontSize(22)
        webViewFormPage.setEditorFontColor(Color.BLACK)
        webViewFormPage.setPadding(10, 10, 10, 10)
        webViewFormPage.setPlaceholder("add detail ....")

        if(webViewFormPage.requestFocus()){
            webViewFormPage.focusEditor()
            ib_AddImage.visibility = View.VISIBLE
        }else {
            ib_AddImage.visibility = View.GONE
        }

        action_undo.setOnClickListener {
            webViewFormPage.undo()
        }

        action_redo.setOnClickListener {
            webViewFormPage.redo()
        }

        action_bold.setOnClickListener {
            webViewFormPage.setBold()
        }

        action_italic.setOnClickListener {
            webViewFormPage.setItalic()
        }

        action_subscript.setOnClickListener {
            webViewFormPage.setSubscript()
        }

        action_superscript.setOnClickListener {
            webViewFormPage.setSuperscript()
        }

        action_strikethrough.setOnClickListener {
            webViewFormPage.setStrikeThrough()
        }

        action_underline.setOnClickListener {
            webViewFormPage.setUnderline()
        }

        action_heading1.setOnClickListener {
            webViewFormPage.setHeading(1)
        }

        action_heading2.setOnClickListener {
            webViewFormPage.setHeading(2)
        }

        action_heading3.setOnClickListener {
            webViewFormPage.setHeading(3)
        }

        action_heading4.setOnClickListener {
            webViewFormPage.setHeading(4)
        }


        action_heading5.setOnClickListener {
            webViewFormPage.setHeading(5)
        }

        action_heading6.setOnClickListener {
            webViewFormPage.setHeading(6)
        }

        action_txt_color.setOnClickListener(object : View.OnClickListener {
            private var isChanged: Boolean = false

            override fun onClick(v: View) {
                webViewFormPage.setTextColor(
                        if (isChanged) {
                            Color.BLACK
                        } else {
                            Color.RED
                        }
                )
                isChanged = !isChanged
            }
        })

        action_bg_color.setOnClickListener(object : View.OnClickListener {
            private var isChanged: Boolean = false

            override fun onClick(v: View) {
                webViewFormPage.setTextBackgroundColor(
                        if (isChanged) {
                            Color.TRANSPARENT
                        } else {
                            Color.YELLOW
                        }
                )
                isChanged = !isChanged
            }
        })

        action_indent.setOnClickListener {
            webViewFormPage.setIndent()
        }

        action_outdent.setOnClickListener {
            webViewFormPage.setOutdent()
        }

        action_align_left.setOnClickListener {
            webViewFormPage.setAlignLeft()
        }

        action_align_center.setOnClickListener {
            webViewFormPage.setAlignCenter()
        }

        action_align_right.setOnClickListener {
            webViewFormPage.setAlignRight()
        }

        action_blockquote.setOnClickListener {
            webViewFormPage.setBlockquote()
        }

        action_insert_bullets.setOnClickListener {
            webViewFormPage.setBullets()
        }

        action_insert_numbers.setOnClickListener {
            webViewFormPage.setNumbers()
        }



        action_insert_link.setOnClickListener {
            webViewFormPage.insertLink(
                    "https://github.com/wasabeef",
                    "career@appman.co.th"
            )
        }

        action_insert_checkbox.setOnClickListener {
            webViewFormPage.insertTodo()
        }

        val dialog = ImageDialog(this)

        dialog.listener = object : ImageDialog.DialogListener {

            override fun onCameraClick() {


                if (checkPermission()) {
                        openCamera()
                } else {
                    requestPermission(REQUEST_PERMISSION_CAMERA)
                }

            }

            override fun onGalleryClick() {
                if (checkPermission()) {
                    openGallery()
                } else {
                    requestPermission(REQUEST_PERMISSION_GALLERY)
                }

            }
        }


        ib_AddImage.setOnClickListener {
            dialog.show()
        }

        ib_back_pageForm.setOnClickListener {
            nPreview = Html.fromHtml(nPreview).toString()
            if (ed_subject_from.text.toString().isNullOrBlank() && nPreview.isNullOrBlank()) {
                finish()
            } else {
                android.support.v7.app.AlertDialog.Builder(this@FormActivity)
                        .setTitle("Are you sure ?")
                        .setMessage("Do you want to close the app?")
                        .setPositiveButton("yes") { dialog, which -> finish() }
                        .setNegativeButton("no") { dialog, which -> }
                        .show()
            }
        }

        webViewFormPage.setOnTextChangeListener { text ->
            var mPreview = text.toString()
            nPreview = mPreview
            PreviewHtml = mPreview

        }

        ib_done_pageForm.setOnClickListener {
            nPreview = Html.fromHtml(nPreview).toString()
            val mEmail = sharedPreference.getString("email", "")
            val dateTime = SimpleDateFormat("dd-MMM-yyyy-HH:mm:ss", Locale.ENGLISH).format(Date())
            val mTimestamp = Date().time.toString()
            val PrimeryKey_id = "${mEmail} $mTimestamp"

            if (ed_subject_from.text.toString().isNullOrBlank() || nPreview.isNullOrBlank()) {

                AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_priority_high_black_24dp)
                        .setTitle("ผิดพลาด")
                        .setMessage("กรุณากรอกข้อมูลใหม่")
                        .show()
            } else {
                val formPage = Data(
                        ed_subject_from.text.toString().trim(),
                        PreviewHtml,
                        dateTime,
                        sharedPreference.getString("img_url", ""),
                        sharedPreference.getString("display_name", ""),
                        sharedPreference.getString("email", ""),
                        PrimeryKey_id
                )
                val intent = Intent().apply {
                    this.putExtra("Data", formPage)
                }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            closeKeyboard()
        }

    }
    private fun openCamera(){
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, createPathForCameraIntent())
        startActivityForResult(cameraIntent, PICK_CAMARA)
    }
    private fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_GALLARY)
    }

    private fun requestPermission(requestCode: Int) {

        ActivityCompat.requestPermissions(
                this@FormActivity,
                arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), requestCode
        )

    }

    fun checkPermission(): Boolean {

        return (ContextCompat.checkSelfPermission(this@FormActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                this@FormActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                this@FormActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)

    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        android.support.v7.app.AlertDialog.Builder(this@FormActivity)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to close this page?")
                .setPositiveButton("yes") { dialog, which -> finish() }
                .setNegativeButton("no") { dialog, which -> }
                .show()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        openCamera()
                }
            }
            REQUEST_PERMISSION_GALLERY -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                        openGallery()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICK_CAMARA -> {
                if (resultCode == Activity.RESULT_OK) {
                    progressDialog.show()
                    if (imageSavedPath != null) {
                        toast("Bitmap resized.")
                        Glide.with(this)
                                .asBitmap()
                                .listener(object : RequestListener<Bitmap?> {
                                    override fun onLoadFailed(
                                            e: GlideException?,
                                            model: Any?,
                                            target: Target<Bitmap?>?,
                                            isFirstResource: Boolean
                                    ): Boolean {
                                        return false
                                    }

                                    override fun onResourceReady(
                                            resource: Bitmap?,
                                            model: Any?,
                                            target: Target<Bitmap?>?,
                                            dataSource: DataSource?,
                                            isFirstResource: Boolean
                                    ): Boolean {
                                        resource?.let {
                                            val rescaleBitmap =
                                                    resizeBitmap(resource, resource.width / 2, resource.height / 2)
                                            uploadImageForCamera(rescaleBitmap.toByteArray())

                                        }
                                        return false
                                    }
                                })
                                .load(imageSavedPath)
                                .into(ivCamera)
                    } else {
                        toast("bitmap not found.")
                    }

                }
            }

            PICK_GALLARY -> {
                if (resultCode == Activity.RESULT_OK) {
                    progressDialog.show()
                    getBitmapFormUriGallery(data?.data)
                }
            }
        }
    }

    private fun createPathForCameraIntent(): Uri? {
        fileName = "image_" + Date().time.toString() + ".jpeg"
        val output: File = File(imagesFolder, fileName)
        imageSavedPath = Uri.fromFile(output)
        return imageSavedPath
    }

    private fun getBitmapFormUriGallery(uri: Uri?) {
        uri?.let {
            val filePath: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = getContentResolver().query(uri, filePath, null, null, null)
            cursor?.moveToFirst()
            val imagePath = cursor?.getString(cursor.getColumnIndex(filePath[0]))
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888
            val bitmap = BitmapFactory.decodeFile(imagePath, options)
            cursor?.close()
            uploadImageForGallery(resizeBitmap(bitmap, bitmap.width / 2, bitmap.height / 2).toByteArray())
        }
    }


    private fun deleteImageForCamera(imageRef: StorageReference) {
        imageRef.delete().addOnSuccessListener() {
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteImageForGallery(imageRef: StorageReference) {
        imageRef.delete().addOnSuccessListener {
            Toast.makeText(this, "Deleted ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImageForCamera(byteArray: ByteArray?) {

        if (byteArray != null) {
            progressDialog.setTitle("Uploading....")
            val imageRef = storageReference!!.child("Image/Camera/" + UUID.randomUUID().toString())
            imageRef.putBytes(byteArray)
                    .addOnSuccessListener { taskSnapShot ->
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Image Uploaded", Toast.LENGTH_SHORT).show()
                        imageRef.downloadUrl.addOnCompleteListener { p0 ->
                            webViewFormPage.insertImage(p0.result.toString(), "Failed")
                        }
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
                    }
                    .addOnProgressListener { taskSnapShot ->
                        val progress = (100.00 * taskSnapShot.bytesTransferred) / taskSnapShot.totalByteCount
                        progressDialog.setMessage("Uploading  " + progress.toInt() + "  % ...")

                    }

        }
    }


    private fun uploadImageForGallery(byteArray: ByteArray?) {

        if (byteArray != null) {

            progressDialog.setTitle("Uploading....")
            val imageRef = storageReference!!.child("Image/Gallery/" + UUID.randomUUID().toString())
            imageRef.putBytes(byteArray)
                    .addOnSuccessListener { taskSnapShot ->
                        Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                        imageRef.downloadUrl.addOnCompleteListener { p0 ->
                            webViewFormPage.insertImage(p0.result.toString(), "Failed")
                        }
                    }
                    .addOnProgressListener { taskSnapShot ->
                        var progress = (100.0 * taskSnapShot.bytesTransferred) / taskSnapShot.totalByteCount
                        progressDialog.setMessage("Uploading " + progress.toInt() + "% ...")
                    }
                    .addOnFailureListener {
                        progressDialog.dismiss()
                        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

                    }
        }
    }


    private fun resizeBitmap(bitmap: Bitmap, width: Int, height: Int): Bitmap {
        return if (bitmap.width > bitmap.height) {
            //Landscape
            if (bitmap.width > 1024) {
                Bitmap.createScaledBitmap(bitmap, width , height , false)
            } else {
                bitmap
            }
        } else {
            //Portrait
            if (bitmap.height > 1024) {
                Bitmap.createScaledBitmap(bitmap, width, height , false)
            } else {
                bitmap
            }
        }
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
