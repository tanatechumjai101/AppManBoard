package com.example.thefirstnewprojectaddtoday28jan62.main.owner

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.main.home.form.FormActivity
import com.example.thefirstnewprojectaddtoday28jan62.main.owner.adapter.OwnerRecyclerAdapter
import com.example.thefirstnewprojectaddtoday28jan62.main.owner.edit.EditActivity
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import com.example.thefirstnewprojectaddtoday28jan62.util.Singleton
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_owner.*
import java.text.SimpleDateFormat
import java.util.*


class PageOwnerFragment : Fragment() {

    lateinit var mUsersIns: DatabaseReference
    lateinit var adapter: OwnerRecyclerAdapter
    var listdata = mutableListOf<Data>()
    lateinit var mActivity: Activity
    lateinit var listMain: RecyclerView
    private lateinit var deleteIcon: Drawable

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = activity!!
        val view = inflater.inflate(R.layout.fragment_owner, container, false)
        listMain = view.findViewById(R.id.rv_owner)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        deleteIcon = ContextCompat.getDrawable(mActivity, R.drawable.ic_delete)!!
        var swipeBackground = ColorDrawable(ContextCompat.getColor(mActivity, android.R.color.holo_red_dark))

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

        val itemTouchHelperCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {

                override fun onMove(
                    p0: RecyclerView,
                    p1: RecyclerView.ViewHolder,
                    p2: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                    val mRootIns = FirebaseDatabase.getInstance().reference
                    val oldList: MutableList<Data> = mutableListOf()
                    val listModify: MutableList<Data> = mutableListOf()
                    oldList.addAll(adapter.Listdata!!)
                    listModify.addAll(adapter.Listdata!!)

                    val deleteIndex = viewHolder.adapterPosition
                    var insertIndex: Int? = null

//                    Delete item from rows //

                    for (i: Int in 0 until listdata.size) {
                        if (listdata[i].id == listModify[deleteIndex].id) {
                            listdata.removeAt(i)
                            mUsersIns.child("Activity").setValue(listdata)
                            listModify.removeAt(deleteIndex)
                            insertIndex = i
                            break
                        }
                    }
                    adapter.Listdata = listModify
                    adapter.notifyItemRemoved(deleteIndex)

                    Snackbar.make(
                        viewHolder.itemView,
                        " ${oldList[deleteIndex].subject} deleted. ", Snackbar.LENGTH_SHORT
                    ).setAction("UNDO") {
                        listModify.add(deleteIndex, oldList[deleteIndex])
                        insertIndex?.let {
                            listdata.add(insertIndex, oldList[deleteIndex])
                        }
                        mUsersIns.child("Activity").setValue(listdata)
                        adapter.Listdata = listModify
                        adapter.notifyItemInserted(deleteIndex)
                    }.show()
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val itemView = viewHolder.itemView
                    val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
                    if (dX > 0) {
                        swipeBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                        deleteIcon.setBounds(
                            itemView.left + iconMargin,
                            itemView.top + iconMargin,
                            itemView.left + iconMargin + deleteIcon.intrinsicWidth,
                            itemView.bottom - iconMargin
                        )
                    } else {
                        swipeBackground.setBounds(
                            itemView.right + dX.toInt(),
                            itemView.top,
                            itemView.right,
                            itemView.bottom
                        )
                        deleteIcon.setBounds(
                            itemView.right - iconMargin - deleteIcon.intrinsicWidth,
                            itemView.top + iconMargin,
                            itemView.right - iconMargin,
                            itemView.bottom - iconMargin
                        )
                    }
                    swipeBackground.draw(c)
                    c.save()

                    if (dX > 0) {
                        c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    } else {
                        c.clipRect(
                            itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom
                        )
                    }
                    deleteIcon.draw(c)
                    c.restore()
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(rv_owner)

        adapter = OwnerRecyclerAdapter(null)
        listMain.adapter = adapter
        listMain.setHasFixedSize(true)
        listMain.layoutManager = LinearLayoutManager(mActivity, LinearLayout.VERTICAL, false)
        adapter.listener = object : OwnerRecyclerAdapter.RecyclerListener {
            override fun onItemClick(position: Int, data: Data) {
                val intent = Intent(mActivity, EditActivity::class.java)
                intent.putExtra("data", data)
                startActivityForResult(intent, 4)
            }
        }
    }


    private fun setListDataFromDataSnapshot(dataSnapshot: DataSnapshot) {
        if (dataSnapshot.value == null) {
            return
        }
        val value = Gson().toJson(dataSnapshot.value)
        val sharedPreference = mActivity.getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        if (value.isNotEmpty()) {
            listdata = Gson().fromJson<MutableList<Data>>(value)
            val listModify: MutableList<Data> = arrayListOf()
            listModify.addAll(listdata)
            for (i: Int in listModify.size - 1 downTo 0) {
                if (listModify[i].email != sharedPreference.getString("email","")) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            4 -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        val dateTime = SimpleDateFormat("dd-MMM-yyyy-HH:mm:ss").format(Date())
                        val newData: Data = data.extras.getParcelable("new_data")!!

                        for (i: Int in 0 until listdata.size) {
                            if (listdata[i].id == newData.id) {
                                listdata!![i].subject = newData.subject
                                listdata!![i].detail = newData.detail
                                break
                            }
                        }
                        mUsersIns.child("Activity").setValue(listdata)

                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
