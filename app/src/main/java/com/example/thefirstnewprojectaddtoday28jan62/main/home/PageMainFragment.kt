package com.example.thefirstnewprojectaddtoday28jan62.main.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.main.home.adapter.HomeAdapter
import com.example.thefirstnewprojectaddtoday28jan62.main.home.form.FormActivity
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_one.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PageMainFragment : Fragment() {

    var adapter: HomeAdapter? = null
    lateinit var mUsersIns: DatabaseReference
    var listdata = ArrayList<Data>()
    lateinit var mActivity: Activity
    val CREATE_FORM = 2539
    lateinit var listMain: RecyclerView

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = activity!!
        val view = inflater.inflate(R.layout.fragment_one, container, false)
        listMain = view.findViewById(R.id.listViewMain)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listMain.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE

        val mRootIns = FirebaseDatabase.getInstance().reference

        listMain.layoutManager = LinearLayoutManager(mActivity, LinearLayout.VERTICAL, false)
        adapter = HomeAdapter(listdata)
        listMain.adapter = adapter

        mUsersIns = mRootIns.child("PageMain")
        mUsersIns.child("Activity").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value == null) {
                    return
                }
                val value = Gson().toJson(dataSnapshot.value)
                Log.d("Test", "value: $value")
                if (value.isNotEmpty()) {
                    listdata = Gson().fromJson<ArrayList<Data>>(value)
                    val dataReverse: ArrayList<Data> = arrayListOf()
                    dataReverse.addAll(listdata)
                    dataReverse.reverse()
                    adapter!!.Listdata = dataReverse
                    adapter!!.notifyDataSetChanged()
                    listMain.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                AlertDialog.Builder(mActivity)
                        .setMessage("Error")
                        .show()
            }
        })



        floating_action_button.setOnClickListener {
            val intent = Intent(mActivity, FormActivity::class.java)
            startActivityForResult(intent, CREATE_FORM)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CREATE_FORM -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        val dateTime = SimpleDateFormat("dd-MMM-yyyy-HH:mm:ss").format(Date())
                        val newData: Data = data.extras.getParcelable("Data")!!
                        val data = Data(newData.subject, newData.detail, dateTime, newData.imageURI, newData.displayname, newData.email)
                        listdata.add(data!!)
                        mUsersIns.child("Activity").setValue(listdata)
                        val dataReverser: ArrayList<Data> = arrayListOf()
                        dataReverser.addAll(listdata)
                        dataReverser.reverse()
                        adapter!!.Listdata = dataReverser
                        adapter!!.notifyDataSetChanged()
                    }
                }
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
