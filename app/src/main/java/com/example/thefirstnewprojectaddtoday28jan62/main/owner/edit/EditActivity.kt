package com.example.thefirstnewprojectaddtoday28jan62.main.owner.edit

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.main.owner.adapter.OwnerRecyclerAdapter
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {
    lateinit var adapter: OwnerRecyclerAdapter
    var listdata = mutableListOf<Data>()
    lateinit var mUsersIns: DatabaseReference
    private var data: Data? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        if (intent.extras != null) {
            data = intent?.extras?.getParcelable("data")
            setDataNow(data)
        }

        btn_save_edit.setOnClickListener {
            data = intent?.extras?.getParcelable("data")

            data!!.subject = ed_subject_edit.text.toString()
            data!!.detail = ed_datail_edit.text.toString()

            intent.putExtra("new_data",data)
            setResult(Activity.RESULT_OK, intent)
            finish ()
        }
        btn_cancel_edit.setOnClickListener {
            finish()

        }
    }

    private fun setDataNow(data: Data?) {
        ed_subject_edit.setText("${data?.subject}")
        ed_datail_edit.setText("${data?.detail}")
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
}
