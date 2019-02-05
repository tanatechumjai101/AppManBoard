package com.example.thefirstnewprojectaddtoday28jan62

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var containerView: Int = -1
    var adapter: MainApadter? = null
    val listdata = ArrayList<Data>()
    var fragmentTransaction: FragmentTransaction? = null

    private lateinit var pagemainFragment: PageMainFragment
    private lateinit var pagechatFragment: PageChatFragment
    private lateinit var pagepersonFragment: PagePersonFragment
    private lateinit var pagesettingFragment: PageSettingFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavMain.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        pagemainFragment = PageMainFragment.newInstance()
        pagechatFragment = PageChatFragment.newInstance()
        pagepersonFragment = PagePersonFragment.newInstance()
        pagesettingFragment = PageSettingFragment.newInstance()

//        supportFragmentManager.beginTransaction()
//                .replace(R.id.frameNav, pagepersonFragment)
//                .addToBackStack(null)
//                .commit()
        supportFragmentManager.beginTransaction()
                .replace(R.id.frameNav, pagemainFragment)
                .addToBackStack(null)
                .commit()

    }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        changePage(item.itemId)

        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putInt("page", item.itemId).apply()
        true
    }

    fun changePage(item: Int) {
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        var result = sharedPreference.getInt("page", 0)
//        val curFrag = this!!.supportFragmentManager.primaryNavigationFragment

        if (item != result) {
//            var fragment = supportFragmentManager.findFragmentByTag(item.toString())
            fragmentTransaction = supportFragmentManager.beginTransaction()

            editor.putInt("page", item).apply()

            when (item) {
                R.id.home -> {
                    fragmentTransaction!!.replace(R.id.frameNav, pagemainFragment, item.toString())
                            .addToBackStack(null)
                            .commit()

                }
                R.id.person -> {
                    fragmentTransaction!!.replace(R.id.frameNav, pagepersonFragment, item.toString())
                            .addToBackStack(null)
                            .commit()
                }
                R.id.chat -> {
                    fragmentTransaction!!.replace(R.id.frameNav, PageChatFragment.newInstance(), item.toString())
                            .addToBackStack(null)
                            .commit()
                }
                R.id.setting -> {
                    fragmentTransaction!!.replace(R.id.frameNav, PageSettingFragment.newInstance(), item.toString())
                            .addToBackStack(null)
                            .commit()
                }
            }
//            if (curFrag != null) {
//                fragmentTransaction!!.hide(curFrag)
//            }
//            fragmentTransaction!!.setPrimaryNavigationFragment(fragment)
//            fragmentTransaction!!.setReorderingAllowed(true)
//            fragmentTransaction!!.addToBackStack(null)
//            fragmentTransaction!!.commit()
        }
    }


    override fun onPause() {
        super.onPause()
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        editor.remove("page")
    }

    override fun onResume() {
        super.onResume()
        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.clear()
        editor.remove("page")
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
            } else if (fragment is PageChatFragment) {
                NavMain.menu.getItem(1).isChecked = true
            } else if (fragment is PageSettingFragment) {
                NavMain.menu.getItem(2).isChecked = true
            } else {

            }
        }
    }
}
