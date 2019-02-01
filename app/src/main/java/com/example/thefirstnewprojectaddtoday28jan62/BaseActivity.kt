package com.example.thefirstnewprojectaddtoday28jan62

import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

open class BaseActivity: AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finish()
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Plase click BACK agian to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }
}