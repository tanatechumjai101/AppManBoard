package com.example.AppManBoard.main.owner.edit

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.AppManBoard.R
import com.example.AppManBoard.main.owner.dialog.ImageDialogEdit
import com.example.AppManBoard.main.owner.edit.viewmodel.EditActivityViewModelDeleted
import com.example.AppManBoard.main.owner.edit.viewmodel.EditActivityViewModelEdit
import com.example.AppManBoard.model.Data
import com.example.AppManBoard.toByteArray
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.toolbar_form_activity.*
import java.io.File
import java.util.*


class EditActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog
    private var fileName: String = ""
    private lateinit var imagesFolder: File
    private var imageSavedPath: Uri? = null
    private var data: Data? = null
    private lateinit var nPreview: String
    private val REQUEST_PERMISSION_CAMERA_EDIT = 360
    private val REQUEST_PERMISSION_GALLERY_EDIT = 720
    private val PICK_CAMARA_EDIT = 1242
    private val PICK_GALLARY_EDIT = 2142
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    private lateinit var vm: EditActivityViewModelEdit
    private lateinit var vn: EditActivityViewModelDeleted
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        vm = ViewModelProviders.of(this).get(EditActivityViewModelEdit::class.java)
         vn =  ViewModelProviders.of(this).get(EditActivityViewModelDeleted::class.java)
        val builder = StrictMode.VmPolicy.Builder()

        StrictMode.setVmPolicy(builder.build())
        imagesFolder = File(Environment.getExternalStorageDirectory(), "AppmanBoard")

        progressDialog = ProgressDialog(this)

        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference

        var x = vm.checkData("hid", "tanate chumjai", "tanate.chu@appman.com", "tanate.chu@appman.co.th 155559562861", "https://lh3.googleusercontent.com/a-/AAuE7mA1JtiOfRPbEpfUbGxh3GYmL33Bvd3N38VfkLqctA", "hi", "18-เม.ย.-2019-10:52:42")
        if (x) {
            Toast.makeText(this@EditActivity,"Edit completed",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@EditActivity,"Edit Failed",Toast.LENGTH_SHORT).show()

        }
        var id = vn.checkData("tanate.chu@appman.co.th 155559562861")
        if (id){
            Toast.makeText(this@EditActivity,"Deleted completed",Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this@EditActivity,"Deleted completed",Toast.LENGTH_SHORT).show()
        }

        if (intent.extras != null) {
            data = intent?.extras?.getParcelable("data")
            setDataNow(data!!)
        }
        webview_edit.setEditorHeight(200)
        webview_edit.setEditorFontSize(22)
        webview_edit.setEditorFontColor(Color.BLACK)
        webview_edit.setPadding(20, 10, 20, 10)
        webview_edit.isEnabled = true

        webview_edit.setOnTextChangeListener { text ->
            var mPreview = text.toString()
            nPreview = mPreview
        }

        if (webview_edit.requestFocus()) {
            webview_edit.focusEditor()

            ib_AddImage.visibility = View.VISIBLE

        } else {

            ib_AddImage.visibility = View.GONE

        }
        val dialog = ImageDialogEdit(this)

        dialog.listener = object : ImageDialogEdit.DialogListener {

            override fun onCameraClick() {

                if (checkPermission()) {
                    openCamera()
                } else {
                    requestPermission(REQUEST_PERMISSION_CAMERA_EDIT)
                }

            }

            override fun onGalleryClick() {
                if (checkPermission()) {
                    openGallery()
                } else {
                    requestPermission(REQUEST_PERMISSION_GALLERY_EDIT)
                }

            }
        }

        if (webview_edit.requestFocus()) {

            webview_edit.focusEditor()

            ib_AddImage.visibility = View.VISIBLE

        } else {

            ib_AddImage.visibility = View.GONE

        }

        ib_AddImage.setOnClickListener {

            dialog.show()

        }


        ib_back_pageForm.setOnClickListener {

            android.support.v7.app.AlertDialog.Builder(this@EditActivity)
                    .setTitle("Are you sure ?")
                    .setMessage("Do you want to close the app?")
                    .setPositiveButton("yes") { dialog, which -> finish() }
                    .setNegativeButton("no") { dialog, which -> }
                    .show()

        }

        ib_done_pageForm.setOnClickListener {

            data = intent?.extras?.getParcelable("data")
            data!!.subject = ed_subject_edit.text.toString().trim()
            data!!.detail = webview_edit.html

            intent.putExtra("new_data", data)
            setResult(Activity.RESULT_OK, intent)
            closeKeyboard()
            finish()

        }

        action_undo_edit.setOnClickListener {
            webview_edit.undo()
        }

        action_redo_edit.setOnClickListener {
            webview_edit.redo()
        }

        action_bold_edit.setOnClickListener {
            webview_edit.setBold()
        }

        action_italic_edit.setOnClickListener {
            webview_edit.setItalic()
        }

        action_subscript_edit.setOnClickListener {
            webview_edit.setSubscript()
        }

        action_superscript_edit.setOnClickListener {
            webview_edit.setSuperscript()
        }

        action_strikethrough_edit.setOnClickListener {
            webview_edit.setStrikeThrough()
        }

        action_underline_edit.setOnClickListener {
            webview_edit.setUnderline()
        }

        action_heading1_edit.setOnClickListener {
            webview_edit.setHeading(1)
        }

        action_heading2_edit.setOnClickListener {
            webview_edit.setHeading(2)
        }

        action_heading3_edit.setOnClickListener {
            webview_edit.setHeading(3)
        }

        action_heading4_edit.setOnClickListener {
            webview_edit.setHeading(4)
        }


        action_heading5_edit.setOnClickListener {
            webview_edit.setHeading(5)
        }

        action_heading6_edit.setOnClickListener {
            webview_edit.setHeading(6)
        }

        action_txt_color_edit.setOnClickListener(object : View.OnClickListener {
            private var isChanged: Boolean = false

            override fun onClick(v: View) {
                webview_edit.setTextColor(
                        if (isChanged) {
                            Color.BLACK
                        } else {
                            Color.RED
                        }
                )
                isChanged = !isChanged
            }
        })

        action_bg_color_edit.setOnClickListener(object : View.OnClickListener {
            private var isChanged: Boolean = false

            override fun onClick(v: View) {
                webview_edit.setTextBackgroundColor(
                        if (isChanged) {
                            Color.TRANSPARENT
                        } else {
                            Color.YELLOW
                        }
                )
                isChanged = !isChanged
            }
        })

        action_indent_edit.setOnClickListener {
            webview_edit.setIndent()
        }

        action_outdent_edit.setOnClickListener {
            webview_edit.setOutdent()
        }

        action_align_left_edit.setOnClickListener {
            webview_edit.setAlignLeft()
        }

        action_align_center_edit.setOnClickListener {
            webview_edit.setAlignCenter()
        }

        action_align_right_edit.setOnClickListener {
            webview_edit.setAlignRight()
        }

        action_blockquote_edit.setOnClickListener {
            webview_edit.setBlockquote()
        }

        action_insert_bullets_edit.setOnClickListener {
            webview_edit.setBullets()
        }

        action_insert_numbers_edit.setOnClickListener {
            webview_edit.setNumbers()
        }



        action_insert_link_edit.setOnClickListener {
            webview_edit.insertLink(
                    "https://github.com/wasabeef",
                    "career@appman.co.th"
            )
        }

        action_insert_checkbox_edit.setOnClickListener {
            webview_edit.insertTodo()
        }
    }

    private fun createPathForCameraIntent(): Uri? {
        fileName = "image_" + Date().time.toString() + ".jpeg"
        val output: File = File(imagesFolder, fileName)
        imageSavedPath = Uri.fromFile(output)
        return imageSavedPath
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, createPathForCameraIntent())
        startActivityForResult(cameraIntent, PICK_CAMARA_EDIT)
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_GALLARY_EDIT)
    }

    private fun requestPermission(requestCode: Int) {

        ActivityCompat.requestPermissions(
                this@EditActivity,
                arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), requestCode
        )

    }

    fun checkPermission(): Boolean {

        return (ContextCompat.checkSelfPermission(
                this@EditActivity,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                this@EditActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                this@EditActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)

    }

    private fun setDataNow(data: Data?) {

        ed_subject_edit.setText("${data?.subject}")
        val body = "<!DOCTYPE html><html><body>"
        val content = body + data!!.detail + "</body></html>"
        webview_edit.html = content

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
                            webview_edit.insertImage(p0.result.toString(), "Failed")
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
            uploadImageForGallery(
                    resizeBitmapGallery(
                            bitmap,
                            (webview_edit.width * 0.8).toInt(),
                            (webview_edit.height * 0.8).toInt()
                    ).toByteArray()
            )
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
                            webview_edit.insertImage(p0.result.toString(), "Failed")
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_CAMERA_EDIT -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                }
            }
            REQUEST_PERMISSION_GALLERY_EDIT -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    openGallery()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICK_CAMARA_EDIT -> {
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
                                                    resizeBitmapCamera(
                                                            resource,
                                                            (resource.width * 0.5).toInt(),
                                                            (resource.height * 0.5).toInt()
                                                    )
                                            uploadImageForCamera(rescaleBitmap.toByteArray())

                                        }
                                        return false
                                    }
                                })
                                .load(imageSavedPath)
                                .into(ivCameraEdit)
                    } else {
                        toast("bitmap not found.")
                    }

                }
            }

            PICK_GALLARY_EDIT -> {
                if (resultCode == Activity.RESULT_OK) {
                    progressDialog.show()
                    getBitmapFormUriGallery(data?.data)
                }
            }
        }
    }

    private fun resizeBitmapCamera(bitmap: Bitmap, width: Int, height: Int): Bitmap {

        return if (bitmap.height > webview_edit.height && bitmap.width > webview_edit.width) {
            //Landscape
            if (bitmap.width < 1024) {
                bitmap
            } else {
                Bitmap.createScaledBitmap(bitmap, width, height, false)
            }
        } else {
            //Portrait
            if (bitmap.height < 1024) {
                bitmap
            } else {
                Bitmap.createScaledBitmap(bitmap, width, height, false)
            }
        }

    }

    private fun resizeBitmapGallery(bitmap: Bitmap, width: Int, height: Int): Bitmap {

        return if (bitmap.width < webview_edit.width) {
            val matrix = Matrix()
            matrix.postRotate(90F)
            //Landscape
            if (bitmap.width < 1024) {
                bitmap
            } else {

                val bitmapRotate =
                        Bitmap.createBitmap(bitmap, 0, 0, (bitmap.width * 0.8).toInt(), (bitmap.height * 0.8).toInt(), matrix, false)
                Bitmap.createScaledBitmap(bitmapRotate, (width / 2).toInt(), (height / 2).toInt(), false)

            }
        } else {
            //Portrait
            if (bitmap.height < 1024) {
                bitmap
            } else {
                val matrix = Matrix()
                matrix.postRotate(360F)
                val bitmapRotate =
                        Bitmap.createBitmap(bitmap, 0, 0, (bitmap.width).toInt(), (bitmap.height).toInt(), matrix, false)
                Bitmap.createScaledBitmap(bitmapRotate, (width / 2).toInt(), (height / 2).toInt(), false)
            }
        }

    }

    override fun onBackPressed() {
//        super.onBackPressed()
        AlertDialog.Builder(this@EditActivity)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to close this page?")
                .setPositiveButton("yes") { dialog, which -> finish() }
                .setNegativeButton("no") { dialog, which -> }
                .show()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
