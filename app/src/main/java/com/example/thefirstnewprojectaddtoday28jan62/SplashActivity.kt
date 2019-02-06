package com.example.thefirstnewprojectaddtoday28jan62

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.splashscreen.*


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashscreen)

        val smallToLarge = AnimationUtils.loadAnimation(this, R.anim.small_to_large)
        smallToLarge.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                val intent = Intent(this@SplashActivity, LoginActivity::class.java)

                startActivity(intent)
                finish()
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
        ivLogo.startAnimation(smallToLarge)


    }
}