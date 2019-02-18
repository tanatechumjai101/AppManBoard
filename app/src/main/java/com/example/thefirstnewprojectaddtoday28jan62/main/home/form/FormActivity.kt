package com.example.thefirstnewprojectaddtoday28jan62.main.home.form

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.base.BaseActivity
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import com.example.thefirstnewprojectaddtoday28jan62.util.Singleton
import kotlinx.android.synthetic.main.activity_form.*
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        Send_text.apply {
            setOnClickListener {
                val dateTime = SimpleDateFormat("dd-MMM-yyyy-HH:mm:ss", Locale.ENGLISH).format(Date())
                val mTimestamp = Date().time.toString()
                val PrimeryKey_id = "${Singleton.email}$mTimestamp"
                if (Subject_text.text.toString().isEmpty() || detel_text.text.toString().isEmpty()) {
                    AlertDialog.Builder(context)
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
            }
        }
        Clear_Text.apply {
            setOnClickListener {
                Subject_text.setText("")
                detel_text.setText("")
            }
        }
        Cancel_page.apply {
            setOnClickListener {
                Subject_text.setText("")
                detel_text.setText("")
                finish()
            }
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
