package com.example.thefirstnewprojectaddtoday28jan62.main.owner

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.LinearLayout
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.main.owner.adapter.OwnerRecyclerAdapter
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import com.example.thefirstnewprojectaddtoday28jan62.util.Singleton
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_one.*
import kotlinx.android.synthetic.main.fragment_owner.*
import java.util.*


class PageOwnerFragment : Fragment() {

    lateinit var mUsersIns: DatabaseReference
    var adapter: OwnerRecyclerAdapter? = null
    var listdata = ArrayList<Data>()
    lateinit var mActivity: Activity
    lateinit var listMain: RecyclerView

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = activity!!
        val view = inflater.inflate(R.layout.fragment_owner, container, false)
        listMain = view.findViewById(R.id.rv_owner)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mRootIns = FirebaseDatabase.getInstance().reference

        mUsersIns = mRootIns.child("PageMain")
        mUsersIns.child("Activity").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                setListDataFromDataSnapshot(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                AlertDialog.Builder(mActivity)
                        .setMessage("Error")
                        .show()
            }
        })

        listMain.layoutManager = LinearLayoutManager(mActivity, LinearLayout.VERTICAL, false)
        adapter = OwnerRecyclerAdapter(listdata)
        listMain.adapter = adapter
        adapter!!.notifyDataSetChanged()

    }


    private fun setListDataFromDataSnapshot(dataSnapshot: DataSnapshot) {
        if (dataSnapshot.value == null) {
            return
        }
        val value = Gson().toJson(dataSnapshot.value)
        if (value.isNotEmpty()) {
            listdata = Gson().fromJson<ArrayList<Data>>(value)
            val listModify: ArrayList<Data> = arrayListOf()
            listModify.addAll(listdata)
            for (i: Int in listModify.size-1 downTo 0) {
                if (listModify[i].email != Singleton.email) {
                    listModify.removeAt(i)
                }
            }
            listModify.reverse()
            adapter!!.Listdata = listModify
            adapter!!.notifyDataSetChanged()
        }
    }

    companion object {
        fun newInstance() = PageOwnerFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
