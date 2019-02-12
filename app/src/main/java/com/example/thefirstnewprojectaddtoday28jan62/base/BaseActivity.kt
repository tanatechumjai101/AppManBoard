package com.example.thefirstnewprojectaddtoday28jan62.base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

open class BaseActivity : AppCompatActivity() {

//    private var doubleBackToExitPressedOnce = true
//    private var doubleBackToExitPressedOnce = false

//    override fun onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            finish()
//            val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
//            val editor = sharedPreference.edit()
//            editor.clear()
//            editor.remove("page")
//            NavMain.menu.performIdentifierAction(R.id.person, 0)
//            NavMain.menu.getItem(3).isChecked = false
//        }
//        this.doubleBackToExitPressedOnce = true
////      Toast.makeText(this, "Please click BACK Again to Exit", Toast.LENGTH_SHORT).show()
//        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 1000)
//    }

//    open fun onActivityCreated(savedInstanceState: Bundle?) {}
}