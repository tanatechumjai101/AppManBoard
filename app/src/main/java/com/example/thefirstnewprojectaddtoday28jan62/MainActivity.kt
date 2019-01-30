package com.example.thefirstnewprojectaddtoday28jan62

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.NonNull
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var containerView: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NavMain.menu.performIdentifierAction(R.id.home,0)
        onReplaceFragment(R.id.frameNav, OneFragment.newInstance())

        NavMain.setOnNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.home -> {
                    popBackStackFragment()
                    onReplaceFragment(R.id.frameNav, OneFragment.newInstance())
                }
                R.id.setting -> {
                    popBackStackFragment()
                    onReplaceFragment(R.id.frameNav, TwoFragment.newInstance())
                }
                R.id.chat -> {
                    popBackStackFragment()
                    onReplaceFragment(R.id.frameNav, ThreeFragment.newInstance())
                }
                R.id.person -> {
                    popBackStackFragment()
                    onReplaceFragment(R.id.frameNav, FourFragment.newInstance())
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        popBackStackFragment()
        startActivity(Intent(this@MainActivity,MainActivity::class.java))
    }

    fun popBackStackFragment() {
        val fm = supportFragmentManager
        fm.popBackStack()
    }

    fun onReplaceFragment(@IdRes containerViewId: Int, @NonNull fragment: Fragment) {
        containerView = containerViewId
        supportFragmentManager.beginTransaction()
            .replace(containerViewId, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    override fun onResume() {
        super.onResume()
        NavMain.menu.performIdentifierAction(R.id.home,0)
    }
}
