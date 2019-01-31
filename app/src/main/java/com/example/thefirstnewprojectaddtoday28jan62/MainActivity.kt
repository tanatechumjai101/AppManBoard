package com.example.thefirstnewprojectaddtoday28jan62

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    var containerView: Int = -1
    var adapter: MainApadter? = null
    val listdata = ArrayList<Data>()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavMain.menu.performIdentifierAction(R.id.home, 0)
        onReplaceFragment(R.id.frameNav, OneFragment.newInstance())

        NavMain.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.home -> {
                    popBackStackFragment()
                    onReplaceFragment(R.id.frameNav, OneFragment.newInstance())
                }

                R.id.chat -> {
                    popBackStackFragment()
                    onReplaceFragment(R.id.frameNav, TwoFragment.newInstance())
                }
                R.id.person -> {
                    popBackStackFragment()
                    onReplaceFragment(R.id.frameNav, ThreeFragment.newInstance())
                }
                R.id.setting -> {
                    popBackStackFragment()
                    onReplaceFragment(R.id.frameNav, FourFragment.newInstance())

                }
            }
            true
        }
        RecyclerView_pageHome.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        adapter = MainApadter(listdata)
        RecyclerView_pageHome.adapter = adapter
        adapter!!.notifyDataSetChanged()
        AddAccount()
    }
    fun AddAccount() {
        for (i in 1..20) {
            listdata.add(Data("${i}", "nate${i}"))
        }
    }
    fun ClearAccount(){
        listdata.removeAll(listdata)
        adapter!!.notifyDataSetChanged()

    }

    fun savaAccount() {
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        val gson = Gson()
        val json = gson.toJson(listdata)
        editor.putString("DATALIST", json)
        editor.commit()

    }

    fun LoadAccount() {
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        if (sharedPreference != null) {
            val data = sharedPreference.getString("DATALIST", null)

            if (data != null) {
                val json = JSONArray(data)

                for (i in 0 until json.length()) {
                    if (i <= listdata.size) {
                        listdata.add(
                            Data(
                                json.getJSONObject(i).getString("index"),
                                json.getJSONObject(i).getString("data")
                            )
                        )
                    }
                }
            }
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        popBackStackFragment()
        startActivity(Intent(this@MainActivity, MainActivity::class.java))
    }

    fun popBackStackFragment() {
        val fm = supportFragmentManager
        fm.popBackStackImmediate()
    }

    fun onReplaceFragment(@IdRes containerViewId: Int, @NonNull fragment: Fragment) {
        containerView = containerViewId
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        supportFragmentManager.beginTransaction()
            .replace(containerViewId, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    override fun onResume() {
        super.onResume()
        NavMain.menu.performIdentifierAction(R.id.home, 0)
    }
}
