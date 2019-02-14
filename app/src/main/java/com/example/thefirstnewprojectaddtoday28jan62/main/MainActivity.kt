package com.example.thefirstnewprojectaddtoday28jan62.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.example.thefirstnewprojectaddtoday28jan62.*
import com.example.thefirstnewprojectaddtoday28jan62.main.owner.PageOwnerFragment
import com.example.thefirstnewprojectaddtoday28jan62.main.home.PageMainFragment
import com.example.thefirstnewprojectaddtoday28jan62.main.home.adapter.HomeAdapter
import com.example.thefirstnewprojectaddtoday28jan62.main.person.PagePersonFragment
import com.example.thefirstnewprojectaddtoday28jan62.main.setting.PageSettingFragment
import com.example.thefirstnewprojectaddtoday28jan62.model.Data
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var pagemainFragment: PageMainFragment
    private lateinit var pagechatFragment: PageOwnerFragment
    private lateinit var pagepersonFragment: PagePersonFragment
    private lateinit var pagesettingFragment: PageSettingFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pagemainFragment = PageMainFragment.newInstance()
        pagechatFragment = PageOwnerFragment.newInstance()
        pagepersonFragment = PagePersonFragment.newInstance()
        pagesettingFragment = PageSettingFragment.newInstance()

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameNav, pagemainFragment)
            .commit()

        NavMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameNav, pagemainFragment)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.person -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameNav, pagepersonFragment)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.chat -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameNav, pagechatFragment)
                    .addToBackStack(null)
                    .commit()
            }
            R.id.setting -> {
                supportFragmentManager.beginTransaction().replace(R.id.frameNav, pagesettingFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
        true
    }

    fun changePage(item: Int) {
//        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        var result = sharedPreference.getInt("page", 0)
//        val curFrag = this!!.supportFragmentManager.primaryNavigationFragment
//
//        if (item != result) {
////            var fragment = supportFragmentManager.findFragmentByTag(item.toString())
//            fragmentTransaction = supportFragmentManager.beginTransaction()
//
//            editor.putInt("page", item).apply()
//            if (curFrag != null) {
//                fragmentTransaction!!.hide(curFrag)
//            }
//            fragmentTransaction!!.setPrimaryNavigationFragment(fragment)
//            fragmentTransaction!!.setReorderingAllowed(true)
//            fragmentTransaction!!.addToBackStack(null)
//            fragmentTransaction!!.commit()
    }


    override fun onPause() {
        super.onPause()
//        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.clear()
//        editor.remove("page")
    }

    override fun onResume() {
        super.onResume()
//        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
//        val editor = sharedPreference.edit()
//        editor.clear()
//        editor.remove("page")
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            // Last of back
        } else {
            super.onBackPressed()
            val fragment = supportFragmentManager.findFragmentById(R.id.frameNav)
            if (fragment is PageMainFragment) {
                NavMain.menu.getItem(0).isChecked = true
            } else if (fragment is PagePersonFragment) {
                NavMain.menu.getItem(3).isChecked = true
            } else if (fragment is PageOwnerFragment) {
                NavMain.menu.getItem(1).isChecked = true
            } else if (fragment is PageSettingFragment) {
                NavMain.menu.getItem(2).isChecked = true
            } else {

            }
        }
    }
}

