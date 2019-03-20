package com.example.AppManBoard.login

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.WindowManager
import com.example.AppManBoard.R
import com.example.AppManBoard.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*


const val RC_SIGN_IN = 123

class LoginActivity : AppCompatActivity() {

    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        btn_signin.visibility = View.VISIBLE
        btn_signin.setSize(SignInButton.SIZE_WIDE)

        btn_signin.setOnClickListener {
            val signInIntent = mGoogleSignInClient?.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@LoginActivity)
            .setTitle("Are you sure ?")
            .setMessage("Do you want to close the app?")
            .setPositiveButton("yes") { dialog , which -> finishAffinity() }
            .setNegativeButton("no") { dialog , which -> }
            .show()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            } catch (e: Exception) {

            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {

        val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("display_name", completedTask.result.displayName ).apply()
        editor.putString("email", completedTask.result.email).apply()
        editor.putString("img_url", completedTask.result.photoUrl.toString()).apply()

        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()


//        if (completedTask.result.email!!.contains("@appman.co.th")) {
//            try {
//                sign_in_button.visibility = View.GONE
//            } catch (e: ApiException) {
//                sign_in_button.visibility = View.GONE
//            }
//            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//        } else {
//            if (mGoogleSignInClient != null) {
//                mGoogleSignInClient!!.signOut()
//            }
//        }

    }
}
