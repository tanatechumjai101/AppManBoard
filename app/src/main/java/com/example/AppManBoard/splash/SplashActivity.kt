package com.example.AppManBoard.splash

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.AppManBoard.R
import com.example.AppManBoard.login.LoginActivity
import com.example.AppManBoard.main.MainActivity
import kotlinx.android.synthetic.main.splashscreen.*


class SplashActivity : AppCompatActivity() {

    private var REQUEST_PERMISSION_READ_AND_WRITE = 1
    private var OPEN_PAGE_LOGIN = 2
    private var OPEN_PAGE_MAIN = 3
    private lateinit var  smallToLarge : Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        smallToLarge = AnimationUtils.loadAnimation(
                this, R.anim.small_to_large)

        ivLogo.startAnimation(smallToLarge)

        if(checkInternet()){

            if (checkPermission()) {

                smallToLarge.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)

                        val email = sharedPreference.getString("email", "")

                        if (email.isNullOrEmpty()) {
                            openPageLogin()
                        } else {
                            openPageMain()
                        }
                    }

                    override fun onAnimationStart(animation: Animation?) {

                    }
                })
            } else {

                requestPermission(REQUEST_PERMISSION_READ_AND_WRITE)
            }

        } else {

            Toast.makeText(baseContext, "No Internet Connection", Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                finishAffinity()
            }, 2000)

        }



    }

    private fun checkInternet(): Boolean {
        val checkInternet = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = checkInternet.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
    private fun openPageLogin() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivityForResult(intent, OPEN_PAGE_LOGIN)
        finish()
    }

    private fun openPageMain() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivityForResult(intent, OPEN_PAGE_MAIN)
        finish()
    }

    private fun requestPermission(requestCode: Int) {

        ActivityCompat.requestPermissions(
                this@SplashActivity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode)
    }

    private fun checkPermission(): Boolean {

        return (ContextCompat.checkSelfPermission(
                this@SplashActivity,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this@SplashActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_PERMISSION_READ_AND_WRITE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)

                    val email = sharedPreference.getString("email", "")

                    if (email.isNullOrEmpty()) {
                        openPageLogin()
                    } else {
                        openPageMain()
                    }

                }else {
                    finish()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OPEN_PAGE_LOGIN -> {
                if (resultCode == Activity.RESULT_OK) {
                    openPageLogin()
                } else {

                }
            }
            OPEN_PAGE_MAIN -> {
                if (requestCode == Activity.RESULT_OK) {
                    openPageMain()
                } else {

                }
            }
        }
    }
}