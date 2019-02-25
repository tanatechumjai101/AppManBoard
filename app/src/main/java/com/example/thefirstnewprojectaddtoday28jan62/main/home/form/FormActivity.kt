package com.example.thefirstnewprojectaddtoday28jan62.main.home.form

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import com.example.thefirstnewprojectaddtoday28jan62.util.Singleton
import kotlinx.android.synthetic.main.activity_form.*
import java.text.SimpleDateFormat
import java.util.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.Toast
import com.example.thefirstnewprojectaddtoday28jan62.main.home.add.image.ImageActivity
import kotlinx.android.synthetic.main.activity_image.*
import kotlinx.android.synthetic.main.toolbar_form_activity.*


class FormActivity : AppCompatActivity() {

    lateinit var camera : ImageButton
//    lateinit var Garally: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)


        ib_done_pageForm.setOnClickListener {
            val dateTime = SimpleDateFormat("dd-MMM-yyyy-HH:mm:ss", Locale.ENGLISH).format(Date())
            val mTimestamp = Date().time.toString()
            val PrimeryKey_id = "${Singleton.email}$mTimestamp"
            if (Subject_text.text.toString().isEmpty() || detel_text.text.toString().isEmpty()) {
                AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_priority_high_black_24dp)
                    .setTitle("ผิดพลาด")
                    .setMessage("กรุณากรอกข้อมูลใหม่")
                    .show()
            } else {
                val formPage = Data(
                    Subject_text.text.toString(),
                    detel_text.text.toString(),
                    dateTime,
                    sharedPreference.getString("img_url",""),
                    sharedPreference.getString("display_name",""),
                    sharedPreference.getString("email",""),
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
        ib_back_pageForm.setOnClickListener {
            if(Subject_text.text.toString().isNullOrEmpty() && detel_text.text.toString().isNullOrEmpty() ){
                Subject_text.setText("")
                detel_text.setText("")
                finish()
            }else {
                android.support.v7.app.AlertDialog.Builder(this@FormActivity)
                    .setTitle("Are you sure ?")
                    .setMessage("Do you want to close the app?")
                    .setPositiveButton("yes") { dialog, which -> finish() }
                    .setNegativeButton("no") { dialog, which -> }
                    .show()
            }
        }

        ib_AddImage.setOnClickListener {
            var dialog = ImageActivity(this)
            dialog.show()
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

}
