package com.example.thefirstnewprojectaddtoday28jan62.splash

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.login.LoginActivity
import com.example.thefirstnewprojectaddtoday28jan62.main.MainActivity
import kotlinx.android.synthetic.main.splashscreen.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        val checkInternet = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = checkInternet.activeNetworkInfo

        val smallToLarge = AnimationUtils.loadAnimation(
            this,
            R.anim.small_to_large
        )

        smallToLarge.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()

                val email = sharedPreference.getString("email", "")

                if (email.isNullOrEmpty()) {
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })

//        if (isInternetConnected) {
//            ivLogo.startAnimation(smallToLarge)//
//        } else {
//showAlertDialog
//        }
        if (networkInfo != null && networkInfo.isConnected) {
            ivLogo.startAnimation(smallToLarge)
        } else {
            Toast.makeText(baseContext, "No Internet Connection", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                finishAffinity()
            }, 2000)
        }

    }
}