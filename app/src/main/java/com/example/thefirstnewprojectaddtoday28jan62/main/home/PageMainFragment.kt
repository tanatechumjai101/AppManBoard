package com.example.thefirstnewprojectaddtoday28jan62.main.home

import android.app.Activity
import android.app.AlertDialog
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
import com.bumptech.glide.Glide
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.main.home.adapter.HomeAdapter
import com.example.thefirstnewprojectaddtoday28jan62.main.home.form.FormActivity
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
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
import android.content.Context




class PageMainFragment : Fragment() {
    var adapter: HomeAdapter? = null
    lateinit var mUsersIns: DatabaseReference
    lateinit var activityReference: DatabaseReference
    var listdata = ArrayList<Data>()
    lateinit var mActivity: Activity
    val CREATE_FORM = 2539
    lateinit var listMain: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var textEmpty: TextView

    lateinit var tv_subject: TextView
    lateinit var tv_detail: TextView
    lateinit var iv_profile_show: ImageView



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

                val inflater = LayoutInflater.from(mActivity)
                val view = inflater.inflate(R.layout.activity_selete, null)

                tv_subject = view.findViewById(R.id.tv_subject_showAlert)
                tv_detail = view.findViewById(R.id.tv_detail_showAlert)
                iv_profile_show = view.findViewById(R.id.iv_profile_showAlert)
                tv_subject.text = data.subject
                tv_detail.text = data.detail

                if (data.imageURI == "null" || data.imageURI.isNullOrEmpty() || data.imageURI == null) {
                    Glide.with(mActivity).load(R.drawable.playstore_icon).into(iv_profile_show)
                } else {
                    Glide.with(mActivity).load(data.imageURI).into(iv_profile_show)
                }

                AlertDialog.Builder(mActivity)
                    .setView(view)
                    .create()
                    .show()

            }
        }

        ed_search.ed_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
                if(ed_search.length() != 0){
                    ib_clear.visibility = View.VISIBLE
                }else {
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


        ib_sort.setOnClickListener {
            val popupMenu = PopupMenu(mActivity, it)
            popupMenu.setOnMenuItemClickListener { item ->
               when(item.itemId){
                   R.id.action_select_sort_time -> {
                       Toast.makeText(mActivity,"Sort by Time",Toast.LENGTH_SHORT).show()
                       true
                   }
                   R.id.action_select_sort_character -> {
                       Toast.makeText(mActivity,"Sort by character",Toast.LENGTH_SHORT).show()
                       true
                   }
                   else -> false
               }
            }
            popupMenu.inflate(R.menu.searchbar)
            try{
                val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldMPopup.isAccessible  = true
                val mPopup = fieldMPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup,true)
            }catch (e: Exception){
                Log.e("Main","Error Showing menu icons.",e)
            }finally {
                popupMenu.show()
            }

        }

        floating_action_button.setOnClickListener {
            val intent = Intent(mActivity, FormActivity::class.java)
            startActivityForResult(intent, CREATE_FORM)
        }
    }

    private fun initListener() {

        firebaseListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.value == null) {
                    progressBar.visibility = View.INVISIBLE
                    textEmpty.visibility = View.VISIBLE
                    adapter!!.notifyDataSetChanged()
                    return
                }

                val value = Gson().toJson(dataSnapshot.value)
                if (value.isNotEmpty()) {
                    listdata = Gson().fromJson<ArrayList<Data>>(value)
                    val dataReverse: ArrayList<Data> = arrayListOf()
                    dataReverse.addAll(listdata)
                    dataReverse.reverse()
                    adapter!!.Listdata = dataReverse
                    adapter!!.notifyDataSetChanged()
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
                        listdata.add(data!!)
                        activityReference.setValue(listdata)
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

    fun filter(text: String) {

        val filteredCourseAry: ArrayList<Data> = ArrayList()
        val courseAry: ArrayList<Data> = listdata
        for (eachCourse in courseAry) {
            if (eachCourse.subject!!.toLowerCase().contains(text.toLowerCase())) {
                filteredCourseAry.add(eachCourse)
            }
        }
        adapter!!.filterList(filteredCourseAry)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.searchbar, menu)
    }
    private fun closeKeyboard(view : View) {

        if (view != null) {
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }



    companion object {
        fun newInstance() = PageMainFragment()
    }

    override fun onPause() {
        super.onPause()
//        activityReference.removeEventListener(firebaseListener)
    }

    override fun onResume() {
        super.onResume()
//        activityReference.addValueEventListener(firebaseListener)
        ed_search.setText("")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

}



