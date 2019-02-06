package com.example.thefirstnewprojectaddtoday28jan62

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_one.*
import java.text.SimpleDateFormat
import java.util.*


class PageMainFragment : Fragment() {

//    private lateinit var database: FirebaseDatabase
//    private lateinit var myRef: DatabaseReference
    var adapter: MainApadter? = null
    val listdata = ArrayList<Data>()
    lateinit var mActivity: Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = activity!!
        return inflater.inflate(R.layout.fragment_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//       var database = FirebaseDatabase.getInstance()
//       var myRef = database.getReference("DisplayName")
        val mRootIns = FirebaseDatabase.getInstance().reference
        val mUsersIns = mRootIns.child("PageMain")

        val mRootRef = FirebaseDatabase.getInstance().reference

        listViewMain.layoutManager = LinearLayoutManager(mActivity, LinearLayout.VERTICAL, false)!!
        adapter = MainApadter(listdata)
        listViewMain.adapter = adapter
        adapter!!.notifyDataSetChanged()


        submit.setOnClickListener {
            val textdata = TextInput.text.toString().trim()

            if (TextInput.text.toString().isEmpty()) {
                AlertDialog.Builder(context)
                    .setTitle("กรุณากรอกข้อมูลใหม่")
                    .show()
            } else {
                val dateTime = SimpleDateFormat("dd-MMM-yyyy-HH:mm:ss").format(Date())
                listdata.add(Data(dateTime, "${TextInput.text}"))



//                myRef.setValue(Data(dateTime, "${TextInput.text}"))

                adapter!!.notifyDataSetChanged()

                mUsersIns.child("${Data(dateTime, "${TextInput.text}")}").setValue("${Singleton.email}")
                TextInput.setText("")
            }
        }
    }

    companion object {
        fun newInstance() = PageMainFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}
