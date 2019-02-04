package com.example.thefirstnewprojectaddtoday28jan62

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    //    private lateinit var vm: ContactFragmentViewModel
    var containerView: Int = -1
    var adapter: MainApadter? = null
    val listdata = ArrayList<Data>()
    var fragmentTransaction: FragmentTransaction? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        NavMain.menu.getItem(0).isChecked = true
        startMain()
        NavMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        super.onCreate(savedInstanceState)
    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        changePage(item.itemId)
        startMain()
        if(NavMain.isClickable == null){
            NavMain.menu.performIdentifierAction(R.id.home, 0)
        }
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putInt("page", item.itemId).apply()
        true
    }

    fun changePage(item: Int) {
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        var result = sharedPreference.getInt("page", 0)
        val curFrag = this!!.supportFragmentManager.primaryNavigationFragment

        if (item != result) {
            var fragment = this!!.supportFragmentManager.findFragmentByTag(item.toString())
            fragmentTransaction = this!!.supportFragmentManager.beginTransaction()
            editor.putInt("page", item).apply()
            if (fragment == null) {

                when (item) {
                    R.id.home -> {
                        fragment = PageMainFragment.newInstance()
                        fragmentTransaction!!.add(R.id.frameNav, fragment, item.toString())
                    }
                    R.id.person -> {
                        fragment = PagePersonFragment.newInstance()
                        fragmentTransaction!!.add(R.id.frameNav, fragment, item.toString())
                    }
                    R.id.chat -> {
                        fragment = PageChatFragment()
                        fragmentTransaction!!.add(R.id.frameNav, fragment, item.toString())
                    }
                    R.id.setting -> {
                        fragment = PageSettingFragment()
                        fragmentTransaction!!.add(R.id.frameNav, fragment, item.toString())
                    }
                }
            } else {
                fragmentTransaction!!.show(fragment)
            }

            if (curFrag != null) {
                fragmentTransaction!!.hide(curFrag)
            }
            fragmentTransaction!!.setPrimaryNavigationFragment(fragment)
            fragmentTransaction!!.setReorderingAllowed(true)
            fragmentTransaction!!.addToBackStack(null)
            fragmentTransaction!!.commit()
        }
    }

    fun startMain() {
        if(NavMain.isClickable == null){
//            NavMain.menu.performIdentifierAction(R.id.home, 0)
            NavMain.menu.getItem(0).isChecked = false
        }

    }

    override fun onPause() {
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        editor.remove("page")
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        editor.remove("page")
    }
}
