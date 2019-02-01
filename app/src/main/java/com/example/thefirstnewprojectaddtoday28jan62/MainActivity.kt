package com.example.thefirstnewprojectaddtoday28jan62

import android.content.Context
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    //    private lateinit var vm: ContactFragmentViewModel
    var containerView: Int = -1
    var adapter: MainApadter? = null
    val listdata = ArrayList<Data>()
    var fragmentTransaction: FragmentTransaction? = null
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        RecyclerView_pageHome.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
//        adapter = MainApadter(listdata)
//        RecyclerView_pageHome.adapter = adapter
//        adapter!!.notifyDataSetChanged()
//        AddAccount()

        NavMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        changePage(item.itemId)
        true
    }

    fun changePage(item: Int) {

        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        var result = sharedPreference.getInt("page", 0)

        if (item != result) {
            var fragment = this!!.supportFragmentManager.findFragmentByTag(item.toString())
            fragmentTransaction = this!!.supportFragmentManager.beginTransaction()
            editor.putInt("page", item).apply()
            if (fragment == null) {
                when (item) {
                    R.id.home -> {
                        fragment = OneFragment.newInstance()
                        fragmentTransaction!!.add(R.id.frameNav, fragment, item.toString())
                    }
                    R.id.person -> {
                        fragment = TwoFragment.newInstance()
                        fragmentTransaction!!.add(R.id.frameNav, fragment, item.toString())
                    }
                    R.id.chat -> {
                        fragment = ThreeFragment()
                        fragmentTransaction!!.add(R.id.frameNav, fragment, item.toString())
                    }
                    R.id.setting -> {
                        fragment = FourFragment()
                        fragmentTransaction!!.add(R.id.frameNav, fragment, item.toString())

                    }
                }
            } else {
                fragmentTransaction!!.show(fragment)

            }


            val curFrag = this!!.supportFragmentManager.primaryNavigationFragment
            if (curFrag != null) {
                fragmentTransaction!!.hide(curFrag)
            }
            fragmentTransaction!!.setPrimaryNavigationFragment(fragment)
            fragmentTransaction!!.setReorderingAllowed(true)
            fragmentTransaction!!.addToBackStack(null)
            fragmentTransaction!!.commit()
        }
    }

//    fun popBackStackFragment() {
//        val fm = supportFragmentManager
//        fm.popBackStackImmediate()
//    }
//
//    fun onReplaceFragment(@IdRes containerViewId: Int, @NonNull fragment: Fragment) {
//        containerView = containerViewId
//        getSupportFragmentManager().beginTransaction().remove(fragment).commit();
//        supportFragmentManager.beginTransaction()
//            .replace(containerViewId, fragment)
//            .addToBackStack(null)
//            .commitAllowingStateLoss()
//    }

    override fun onPause() {
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        NavMain.menu.performIdentifierAction(R.id.home, 0)
    }
}
