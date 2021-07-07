package com.example.AppManBoard.main.owner

import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import com.example.AppManBoard.R
import com.example.AppManBoard.main.owner.adapter.OwnerRecyclerAdapter
import com.example.AppManBoard.main.owner.edit.EditActivity
import com.example.AppManBoard.main.owner.viewmodel.PageOwnerViewModel
import com.example.AppManBoard.model.Data
import com.google.firebase.database.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_owner.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlin.collections.ArrayList


class PageOwnerFragment : Fragment() {
    var shredPref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null
    lateinit var adapter: OwnerRecyclerAdapter
    var listdata = ArrayList<Data>()
    lateinit var mActivity: Activity
    lateinit var listMain: RecyclerView
    private lateinit var deleteIcon: Drawable
    private lateinit var firebaseListener: ValueEventListener
    lateinit var mUsersIns: DatabaseReference

    lateinit var activityReference: DatabaseReference
    private var switchPopUp: Int = 1
    private var dataSortByCharactor: Int = 2
    private var dataSortByReverse: Int = 1
    var listshow: ArrayList<Data> = arrayListOf()
    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)
    lateinit var textEmpty: TextView
    private var DATA_OWNER = 4
    private lateinit var mRootIns: DatabaseReference
    private lateinit var checkDataViewModelViewModel: PageOwnerViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = activity!!
        val view = inflater.inflate(R.layout.fragment_owner, container, false)
        listMain = view.findViewById<RecyclerView>(R.id.rv_owner)
        textEmpty = view.findViewById(R.id.tv_showData)
        checkDataViewModelViewModel = ViewModelProviders.of(this).get(PageOwnerViewModel::class.java)
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        shredPref = mActivity.getSharedPreferences("MENU_SORT", Context.MODE_PRIVATE)
        editor = shredPref!!.edit()
        deleteIcon = ContextCompat.getDrawable(mActivity, R.drawable.ic_delete)!!
        var swipeBackground = ColorDrawable(ContextCompat.getColor(mActivity, android.R.color.holo_red_dark))
        listMain.layoutManager = LinearLayoutManager(mActivity, LinearLayout.VERTICAL, false)
        adapter = OwnerRecyclerAdapter(listdata)
        listMain.adapter = adapter
        mRootIns = FirebaseDatabase.getInstance().reference
        mUsersIns = mRootIns.child("PageMain")
        activityReference = mUsersIns.child("Activity")
        activityReference.addValueEventListener(firebaseListener)
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

        var checkSearch = checkDataViewModelViewModel.checkData("rear")

        if(checkSearch){
//            Toast.makeText(mActivity,"Search completed", Toast.LENGTH_SHORT).show()

        }else {

//            Toast.makeText(mActivity,"Search completed",Toast.LENGTH_SHORT).show()

        }

        if (switchPopUp == 1) {

            ib_sort.setImageResource(R.drawable.ic_time)
            adapter.notifyDataSetChanged()

        } else if (switchPopUp == 2) {

            ib_sort.setImageResource(R.drawable.ic_sort_by_alpha_black_24dp)
            adapter.notifyDataSetChanged()
        }
        if(listdata.isEmpty()){
            listdata.clear()
            adapter.notifyDataSetChanged()
        }

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
                        val oldList: ArrayList<Data> = ArrayList()
                        val listModify: ArrayList<Data> = ArrayList()

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
                                listdata.add(deleteIndex, oldList[deleteIndex])

                            }
                            mUsersIns.child("Activity").setValue(listdata)
                            sortByInit(listModify)
                            if (oldList.isNotEmpty()) {
                                adapter.Listdata = oldList
                                adapter.notifyItemInserted(deleteIndex)
                            }
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
                startActivityForResult(intent, DATA_OWNER)
            }
        }

        ed_search.ed_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(listdata.isNotEmpty()){
                    filter(s.toString())
                }else {
                    listshow = listdata
                    adapter.notifyDataSetChanged()
                }

                if (ed_search.length() > 0) {
                    ib_clear.visibility = View.VISIBLE
                } else {
                    ib_clear.visibility = View.INVISIBLE
                }

                ib_clear.setOnClickListener {

                    ed_search.setText("")

                    closeKeyboard(view)
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

//      sort
        ib_sort.setOnClickListener {
            val popupMenu = PopupMenu(mActivity, it)
            popupMenu.setOnMenuItemClickListener { item ->

                when (item.itemId) {
                    R.id.action_select_sort_time -> {

                        switchPopUp = 1
                        ib_sort.setImageResource(R.drawable.ic_time)
                        editor!!.putInt("sort", switchPopUp).apply()
                        adapter.Listdata = listshow
                        adapter.Listdata?.reverse()
                        adapter.notifyDataSetChanged()
                        true

                    }
                    R.id.action_select_sort_character -> {

                        switchPopUp = 2
                        ib_sort.setImageResource(R.drawable.ic_sort_by_alpha_black_24dp)
                        editor!!.putInt("sort", switchPopUp).apply()
                        adapter?.Listdata?.sortWith(compareBy { it.subject })
                        adapter?.notifyDataSetChanged()

                        true

                    }
                    else -> false
                }

            }
            popupMenu.inflate(R.menu.searchbar)

            if (switchPopUp == 1) {

                popupMenu.menu.findItem(R.id.action_select_sort_time).isEnabled = false
                popupMenu.menu.findItem(R.id.action_select_sort_character).isEnabled = true


            } else if (switchPopUp == 2) {

                popupMenu.menu.findItem(R.id.action_select_sort_time).isEnabled = true
                popupMenu.menu.findItem(R.id.action_select_sort_character).isEnabled = false

            }
            try {
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                        .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(mPopup, true)
            } catch (e: Exception) {
                Log.e("Main", "Error Showing menu icons.", e)
            } finally {
                popupMenu.show()
            }

        }
    }


    private fun setListDataFromDataSnapshot(dataSnapshot: DataSnapshot) {
        var listModify: ArrayList<Data> = arrayListOf()
        if (dataSnapshot.value == null) {
            textEmpty.visibility = View.VISIBLE
            return
        } else {

            val value = Gson().toJson(dataSnapshot.value)
            val sharedPreference = mActivity.getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
            if (value.isNotEmpty()) {
                listdata = Gson().fromJson<ArrayList<Data>>(value)

                listModify.addAll(listdata)
                for (i: Int in listModify.size - 1 downTo 0) {
                    if (listModify[i].email != sharedPreference.getString("email", "")) {
                        listModify.removeAt(i)
                    }
                }
                textEmpty.visibility = View.INVISIBLE
                listModify.reverse()
                adapter.Listdata = listModify
                if (listModify.isNotEmpty()) {
                    listshow = listModify
                }
                checkDateOwner(listshow)
                adapter.notifyDataSetChanged()
            }else {
                listModify.clear()
                listshow.clear()
                adapter.notifyDataSetChanged()
            }

        }
    }

    private fun checkDateOwner(listshow: ArrayList<Data>) {
        if (listshow.size == 0) {
            textEmpty.visibility = View.VISIBLE
        } else {
            textEmpty.visibility = View.INVISIBLE
        }
    }

    private fun initListener() {

        firebaseListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value == null) {
                    textEmpty.visibility = View.VISIBLE
                    listdata.clear()
                    adapter.notifyDataSetChanged()
                    return
                }

                val value = Gson().toJson(dataSnapshot.value)
                if (value.isNotEmpty()) {
                    listdata = Gson().fromJson<ArrayList<Data>>(value)
                    val dataReverse: ArrayList<Data> = arrayListOf()
                    dataReverse.addAll(listdata)
                    adapter.Listdata = dataReverse
                    adapter.notifyDataSetChanged()
                    sortByInit(dataReverse)
                    textEmpty.visibility = View.INVISIBLE
                    listMain.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                AlertDialog.Builder(mActivity)
                        .setMessage("Error")
                        .show()
            }
        }
    }


    private fun sortByInit(dataReverse: ArrayList<Data>) {

        val getInit = shredPref!!.getInt("sort", dataSortByReverse)


        if (getInit == dataSortByReverse) {
            if (listdata.isNotEmpty()) {
                dataReverse.reverse()
                adapter.notifyDataSetChanged()
            }

        } else if (getInit == dataSortByCharactor) {
            if (listdata.isNotEmpty()) {
                adapter.Listdata?.sortWith(compareBy { it.subject })
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun filter(text: String) {

        val filteredCourseAry: ArrayList<Data> = ArrayList()
        val courseAry: MutableList<Data> = listshow
        for (eachCourse in courseAry) {
            if (eachCourse.subject!!.toLowerCase().contains(text.toLowerCase())) {
                filteredCourseAry.add(eachCourse)
            }
        }
        adapter.filterList(filteredCourseAry)
    }


    companion object {
        fun newInstance() = PageOwnerFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {

            DATA_OWNER -> {
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null) {

                        val newData: Data = data.extras.getParcelable("new_data")!!
                        if (newData != null) {
                            for (i: Int in 0 until listdata.size) {
                                if (listdata[i].id == newData.id) {
                                    listdata!![i].subject = newData.subject
                                    listdata!![i].detail = newData.detail
                                    break
                                }
                            }
                        }
                        if (listdata.isNotEmpty()) {
                            mUsersIns.child("Activity").setValue(listdata)
                            sortByInit(listdata)
                        }
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun closeKeyboard(view: View) {

        if (view != null) {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        if (listdata.isEmpty()) {
            listdata = listshow
            adapter.notifyDataSetChanged()
        } else {
            adapter.notifyDataSetChanged()
        }
        ed_search.setText("")

    }
}
