package com.example.AppManBoard.main.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import com.example.AppManBoard.R
import com.example.AppManBoard.main.home.adapter.HomeAdapter
import com.example.AppManBoard.main.home.form.FormActivity
import com.example.AppManBoard.model.Data
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_one.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.view.inputmethod.InputMethodManager
import android.content.Context.*
import android.content.SharedPreferences


class PageMainFragment : Fragment() {

    var shredPref: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    var adapter: HomeAdapter? = null
    lateinit var mUsersIns: DatabaseReference
    lateinit var activityReference: DatabaseReference
    var listdata = ArrayList<Data>()
    lateinit var mActivity: Activity
    val CREATE_FORM = 2539
    lateinit var listMain: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var textEmpty: TextView

    private var switchPopUp: Int = 1
    private var dataSortByCharactor: Int = 2
    private var dataSortByReverse: Int = 1
    private lateinit var firebaseListener: ValueEventListener

    inline fun <reified T> Gson.fromJson(json: String) = this.fromJson<T>(json, object : TypeToken<T>() {}.type)


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = activity!!

        val view = inflater.inflate(R.layout.fragment_one, container, false)
        progressBar = view.findViewById(R.id.progressBar)
        listMain = view.findViewById(R.id.listViewMain)
        textEmpty = view.findViewById(R.id.tv_empty)


        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initListener()

        shredPref = mActivity.getSharedPreferences("MENU_SORT", Context.MODE_PRIVATE)
        editor = shredPref!!.edit()

        listMain.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE

        val mRootIns = FirebaseDatabase.getInstance().reference

        listMain.layoutManager = LinearLayoutManager(mActivity, LinearLayout.VERTICAL, false)
        adapter = HomeAdapter(listdata)

        listMain.adapter = adapter
        mUsersIns = mRootIns.child("PageMain")
        activityReference = mUsersIns.child("Activity")
        activityReference.addValueEventListener(firebaseListener)

        adapter!!.listener = object : HomeAdapter.RecyclerListener_pageHome {

            override fun onItemClick(position: Int, data: Data) {
                val dataString = Gson().toJson(data)
                val dialogFragment = ContentDialogFragment.newInstance(dataString)
                dialogFragment.show(fragmentManager, "")
            }
        }

        setIcon()



        ed_search.ed_search.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())

                if (ed_search.length() != 0) {
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

                        timeSet()
                        true

                    }
                    R.id.action_select_sort_character -> {

                        CharSet()

                        true

                    }
                    else -> false
                }

            }

            popupMenu.inflate(R.menu.searchbar)
            setEnabledIcon(popupMenu)

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

        // Create Board
        floating_action_button.setOnClickListener {
            val intent = Intent(mActivity, FormActivity::class.java)
            startActivityForResult(intent, CREATE_FORM)
        }
    }

    private fun setEnabledIcon(popupMenu: PopupMenu) {
        if (switchPopUp == 1) {

            popupMenu.menu.findItem(R.id.action_select_sort_time).isEnabled = false
            popupMenu.menu.findItem(R.id.action_select_sort_character).isEnabled = true


        } else if (switchPopUp == 2) {

            popupMenu.menu.findItem(R.id.action_select_sort_time).isEnabled = true
            popupMenu.menu.findItem(R.id.action_select_sort_character).isEnabled = false

        }
    }

    private fun CharSet() {
        switchPopUp = 2
        ib_sort.setImageResource(R.drawable.ic_sort_by_alpha_black_24dp)
        editor!!.putInt("sort", switchPopUp).apply()
        adapter?.Listdata?.sortWith(compareBy { it.subject })
        adapter?.notifyDataSetChanged()
    }

    private fun timeSet() {
        switchPopUp = 1
        ib_sort.setImageResource(R.drawable.ic_time)
        editor!!.putInt("sort", switchPopUp).apply()
        adapter?.Listdata = listdata
        adapter?.Listdata?.reverse()
        adapter!!.notifyDataSetChanged()
    }

    private fun setIcon() {
        if (switchPopUp == 1) {

            ib_sort.setImageResource(R.drawable.ic_time)
            adapter?.notifyDataSetChanged()

        } else if (switchPopUp == 2) {

            ib_sort.setImageResource(R.drawable.ic_sort_by_alpha_black_24dp)
            adapter?.notifyDataSetChanged()

        }
    }

    private fun sortByInit(dataReverse: ArrayList<Data>) {

        val getInit = shredPref!!.getInt("sort", dataSortByReverse)


        if (getInit == dataSortByReverse) {
            dataReverse.reverse()
            adapter?.notifyDataSetChanged()

        } else if (getInit == dataSortByCharactor) {

            adapter?.Listdata?.sortWith(compareBy { it.subject })
            adapter?.notifyDataSetChanged()

        }


    }

    private fun initListener() {

        firebaseListener = object : ValueEventListener {


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value == null) {
                    progressBar.visibility = View.INVISIBLE
                    textEmpty.visibility = View.VISIBLE
                    listdata.clear()
                    adapter!!.notifyDataSetChanged()
                    return
                }

                val value = Gson().toJson(dataSnapshot.value)
                if (value.isNotEmpty()) {
                    listdata = Gson().fromJson<ArrayList<Data>>(value)
                    val dataReverse: ArrayList<Data> = arrayListOf()
                    dataReverse.addAll(listdata)
                    adapter!!.Listdata = dataReverse
                    adapter!!.notifyDataSetChanged()
                    sortByInit(dataReverse)
                    textEmpty.visibility = View.INVISIBLE
                    listMain.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                AlertDialog.Builder(mActivity)
                        .setMessage("Error")
                        .show()
            }
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
                        val data = Data(
                                newData.subject,
                                newData.detail,
                                dateTime,
                                newData.imageURI,
                                newData.displayname,
                                newData.email,
                                newData.id
                        )
                        listdata.add(data)
                        activityReference.setValue(listdata)
                        val dataReverser: ArrayList<Data> = arrayListOf()
                        dataReverser.addAll(listdata)

                        adapter?.Listdata = dataReverser
                        adapter!!.notifyDataSetChanged()
                        sortByInit(dataReverser)
                    }
                }
            }
        }
    }

    fun filter(text: String) {

        val filteredCourseAry: ArrayList<Data> = ArrayList()
        val courseAry: ArrayList<Data> = listdata

        for (eachCourse in courseAry) {

            if (eachCourse.subject.toLowerCase().contains(text.toLowerCase())) {
                filteredCourseAry.add(eachCourse)
            }
        }
        filterList(filteredCourseAry)

    }

    fun filterList(filteredCourseList: ArrayList<Data>) {
        adapter?.Listdata = filteredCourseList
        sortByInit(filteredCourseList)
        adapter!!.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.searchbar, menu)
    }

    private fun closeKeyboard(view: View) {

        if (view != null) {
            val imm = activity!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    companion object {
        fun newInstance() = PageMainFragment()
    }

    override fun onResume() {
        super.onResume()

        ed_search.setText("")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

}



