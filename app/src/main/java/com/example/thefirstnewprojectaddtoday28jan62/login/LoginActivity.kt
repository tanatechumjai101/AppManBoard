package com.example.thefirstnewprojectaddtoday28jan62.login

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.View
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.util.Singleton
import com.example.thefirstnewprojectaddtoday28jan62.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login.*


const val RC_SIGN_IN = 123

class LoginActivity : AppCompatActivity() {

    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sign_in_button.visibility = View.VISIBLE
        sign_in_button.setSize(SignInButton.SIZE_WIDE)
        sign_in_button.setOnClickListener {
            val signInIntent = mGoogleSignInClient?.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@LoginActivity)
                .setTitle("Are you sure ?")
                .setMessage("Do you want to close the app?")
                .setPositiveButton("yes") { dialog, which -> finishAffinity() }
                .setNegativeButton("no") { dialog, which -> }
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
        Singleton.email = completedTask.result.email ?: "example@email.com"
        Singleton.displayName = completedTask.result.displayName ?: "Member"
        if (completedTask.result.photoUrl.toString().isNullOrEmpty()) {
            Singleton.imageUrl = "data:image/jpeg;base64,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"
        } else {
            Singleton.imageUrl = ""+completedTask.result.photoUrl.toString()
        }

        try {
            sign_in_button.visibility = View.GONE
        } catch (e: ApiException) {
            sign_in_button.visibility = View.GONE
        }
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
