package com.example.thefirstnewprojectaddtoday28jan62.main.home.form

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.View
import com.example.thefirstnewprojectaddtoday28jan62.R
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.example.thefirstnewprojectaddtoday28jan62.main.home.add.image.ImageDialog
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import jp.wasabeef.richeditor.RichEditor
import kotlinx.android.synthetic.main.activity_form.*
import kotlinx.android.synthetic.main.toolbar_form_activity.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.os.Environment
import android.os.StrictMode
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.thefirstnewprojectaddtoday28jan62.toByteArray
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class FormActivity : AppCompatActivity() {

    private lateinit var progressDialog: ProgressDialog
    private var bitmap: Bitmap? = null
    lateinit var mEditor: RichEditor
    lateinit var Maction_undo: ImageView
    lateinit var Maction_redo: ImageView
    lateinit var Maction_bold: ImageView
    lateinit var Maction_italic: ImageView
    lateinit var Maction_subscript: ImageView
    lateinit var Maction_superscript: ImageView
    lateinit var Maction_strikethrough: ImageView
    lateinit var Maction_underline: ImageView
    lateinit var Maction_heading1: ImageView
    lateinit var Maction_heading2: ImageView
    lateinit var Maction_heading3: ImageView
    lateinit var Maction_heading4: ImageView
    lateinit var Maction_heading5: ImageView
    lateinit var Maction_heading6: ImageView
    lateinit var Maction_txt_color: ImageView
    lateinit var Maction_bg_color: ImageView
    lateinit var Maction_indent: ImageView
    lateinit var Maction_outdent: ImageView
    lateinit var Maction_align_left: ImageView
    lateinit var Maction_align_center: ImageView
    lateinit var Maction_align_right: ImageView
    lateinit var Maction_blockquote: ImageView
    lateinit var Maction_insert_bullets: ImageView
    lateinit var Maction_insert_numbers: ImageView
    lateinit var Maction_insert_image: ImageView
    lateinit var Maction_insert_link: ImageView
    lateinit var Maction_insert_checkbox: ImageView
    private var fileName: String = ""

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

        Maction_undo = findViewById(R.id.action_undo)
        Maction_redo = findViewById(R.id.action_redo)
        Maction_bold = findViewById(R.id.action_bold)
        Maction_italic = findViewById(R.id.action_italic)
        Maction_subscript = findViewById(R.id.action_subscript)
        Maction_superscript = findViewById(R.id.action_superscript)
        Maction_strikethrough = findViewById(R.id.action_strikethrough)
        Maction_underline = findViewById(R.id.action_underline)
        Maction_heading1 = findViewById(R.id.action_heading1)
        Maction_heading2 = findViewById(R.id.action_heading2)
        Maction_heading3 = findViewById(R.id.action_heading3)
        Maction_heading4 = findViewById(R.id.action_heading4)
        Maction_heading5 = findViewById(R.id.action_heading5)
        Maction_heading6 = findViewById(R.id.action_heading6)
        Maction_txt_color = findViewById(R.id.action_txt_color)
        Maction_bg_color = findViewById(R.id.action_bg_color)
        Maction_indent = findViewById(R.id.action_indent)
        Maction_outdent = findViewById(R.id.action_outdent)
        Maction_align_left = findViewById(R.id.action_align_left)
        Maction_align_center = findViewById(R.id.action_align_center)
        Maction_align_right = findViewById(R.id.action_align_right)
        Maction_blockquote = findViewById(R.id.action_blockquote)
        Maction_insert_bullets = findViewById(R.id.action_insert_bullets)
        Maction_insert_numbers = findViewById(R.id.action_insert_numbers)
        Maction_insert_image = findViewById(R.id.action_insert_image)
        Maction_insert_link = findViewById(R.id.action_insert_link)
        Maction_insert_checkbox = findViewById(R.id.action_insert_checkbox)

        mEditor = findViewById(R.id.editor) as RichEditor
        mEditor.setEditorHeight(200)
        mEditor.setEditorFontSize(22)
        mEditor.setEditorFontColor(Color.BLACK)
        mEditor.setPadding(10, 10, 10, 10)
        mEditor.setPlaceholder("add detail ....")




        Maction_undo.setOnClickListener(View.OnClickListener { mEditor.undo() })

        Maction_redo.setOnClickListener(View.OnClickListener { mEditor.redo() })

        Maction_bold.setOnClickListener(View.OnClickListener { mEditor.setBold() })

        Maction_italic.setOnClickListener(View.OnClickListener { mEditor.setItalic() })

        Maction_subscript.setOnClickListener(View.OnClickListener { mEditor.setSubscript() })

        Maction_superscript.setOnClickListener(View.OnClickListener { mEditor.setSuperscript() })

        Maction_strikethrough.setOnClickListener(View.OnClickListener { mEditor.setStrikeThrough() })

        Maction_underline.setOnClickListener(View.OnClickListener { mEditor.setUnderline() })

        Maction_heading1.setOnClickListener(View.OnClickListener { mEditor.setHeading(1) })

        Maction_heading2.setOnClickListener(View.OnClickListener { mEditor.setHeading(2) })

        Maction_heading3.setOnClickListener(View.OnClickListener { mEditor.setHeading(3) })

        Maction_heading4.setOnClickListener(View.OnClickListener { mEditor.setHeading(4) })

        Maction_heading5.setOnClickListener(View.OnClickListener { mEditor.setHeading(5) })

        Maction_heading6.setOnClickListener(View.OnClickListener { mEditor.setHeading(6) })

        Maction_txt_color.setOnClickListener(object : View.OnClickListener {
            private var isChanged: Boolean = false

            override fun onClick(v: View) {
                mEditor.setTextColor(if (isChanged) Color.BLACK else Color.RED)
                isChanged = !isChanged
            }
        })

        Maction_bg_color.setOnClickListener(object : View.OnClickListener {
            private var isChanged: Boolean = false

            override fun onClick(v: View) {
                mEditor.setTextBackgroundColor(if (isChanged) Color.TRANSPARENT else Color.YELLOW)
                isChanged = !isChanged
            }
        })

        Maction_indent.setOnClickListener(View.OnClickListener { mEditor.setIndent() })

        Maction_outdent.setOnClickListener(View.OnClickListener { mEditor.setOutdent() })

        Maction_align_left.setOnClickListener(View.OnClickListener { mEditor.setAlignLeft() })

        Maction_align_center.setOnClickListener(View.OnClickListener { mEditor.setAlignCenter() })

        Maction_align_right.setOnClickListener(View.OnClickListener { mEditor.setAlignRight() })

        Maction_blockquote.setOnClickListener(View.OnClickListener { mEditor.setBlockquote() })

        Maction_insert_bullets.setOnClickListener(View.OnClickListener { mEditor.setBullets() })

        Maction_insert_numbers.setOnClickListener(View.OnClickListener { mEditor.setNumbers() })

        Maction_insert_image.setOnClickListener(View.OnClickListener {
            mEditor.insertImage(
                    "https://scontent.fbkk2-8.fna.fbcdn.net/v/t1.0-9/52797687_2298520613710284_7860353166057930752_n.jpg?_nc_cat=105&_nc_eui2=AeGlIcW8ccOQS0EYl_DQjA3BiIBTyy7fgG77qtPmq4hvR6jwPKjFXzJEheuFG4AxuMwYJsMr9zp75bDEeKO4MC3l8IZgLfFnnijkjv0E1rht4Q&_nc_ht=scontent.fbkk2-8.fna&oh=65f853c7e25b17138e5e90cf3831207c&oe=5CECF51F",
                    "Error"
            )
        })

        Maction_insert_link.setOnClickListener(View.OnClickListener {
            mEditor.insertLink(
                    "https://github.com/wasabeef",
                    "career@appman.co.th"
            )
        })
        Maction_insert_checkbox.setOnClickListener(View.OnClickListener { mEditor.insertTodo() })

        val dialog = ImageDialog(this)
        dialog.listener = object : ImageDialog.DialogListener {
            override fun onCameraClick() {

                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, createPathForCameraIntent())
                startActivityForResult(cameraIntent, PICK_CAMARA)
            }

            override fun onGalleryClick() {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, PICK_GALLARY)
            }
        }

        ib_AddImage.setOnClickListener {
            dialog.show()
        }

        ib_back_pageForm.setOnClickListener {
            nPreview = Html.fromHtml(nPreview).toString()
            if (Subject_text.text.toString().isNullOrBlank() && nPreview.isNullOrBlank()) {
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

        mEditor.setOnTextChangeListener { text ->
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

            if (Subject_text.text.toString().isNullOrBlank() || nPreview.isNullOrBlank()) {

                AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_priority_high_black_24dp)
                        .setTitle("ผิดพลาด")
                        .setMessage("กรุณากรอกข้อมูลใหม่")
                        .show()
            } else {
                val formPage = Data(
                        Subject_text.text.toString(),
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICK_CAMARA -> {
                if (resultCode == Activity.RESULT_OK) {
                    progressDialog.show()
                    if (imageSavedPath != null) {
//                        uploadImageForCamera(imageSavedPath!!)
                        toast("Bitmap resized.")
                        Glide.with(this)
                                .asBitmap()
                                .listener(object : RequestListener<Bitmap?> {
                                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap?>?, isFirstResource: Boolean): Boolean {
                                        return false
                                    }
                                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                        resource?.let {
                                            val rescaleBitmap = resizeBitmap(resource, resource.width / 2, resource.height / 2)
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
        fileName = "image_" + Date().time.toString() + ".jpg"
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
            uploadImageForGallery(resizeBitmap(bitmap, bitmap.width/2, bitmap.height/2).toByteArray())
        }
    }

    private fun uploadImageForCamera(byteArray: ByteArray?) {

        if (byteArray != null) {
            progressDialog.setTitle("Uploading....")
            val imageRef = storageReference!!.child("Image/Camera/" + UUID.randomUUID().toString())
            imageRef.putBytes(byteArray)
                    .addOnSuccessListener {taskSnapShot->
                        progressDialog.dismiss()
                        Toast.makeText(applicationContext, "Image Uploaded", Toast.LENGTH_SHORT).show()
                        imageRef.downloadUrl.addOnCompleteListener { p0 ->
                            mEditor.insertImage(p0.result.toString(),"Failed")
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
            var progress: Double = 100.00
            val imageRef = storageReference!!.child("Image/Gallery/" + UUID.randomUUID().toString())
            imageRef.putBytes(byteArray)
                    .addOnSuccessListener {taskSnapShot->
                        Toast.makeText(this, "Image Uploaded", Toast.LENGTH_SHORT).show()
                        progressDialog.dismiss()
                        imageRef.downloadUrl.addOnCompleteListener { p0 ->
//                            Log.d("TEST", "Uri: ${p0.result.toString()}")
                            mEditor.insertImage(p0.result.toString(),"Failed")}
                    }
                    .addOnProgressListener { taskSnapShot ->
                        progress = (100.0 * taskSnapShot.bytesTransferred) / taskSnapShot.totalByteCount
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
                Bitmap.createScaledBitmap(bitmap, width, height, false)
            } else {
                bitmap
            }
        } else {
            //Portrait
            if (bitmap.height > 1024) {
                Bitmap.createScaledBitmap(bitmap, width, height, false)
            } else {
                bitmap
            }
        }
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
