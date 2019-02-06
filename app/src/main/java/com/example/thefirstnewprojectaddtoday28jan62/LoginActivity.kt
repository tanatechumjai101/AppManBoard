package com.example.thefirstnewprojectaddtoday28jan62

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.toast


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
        Singleton.imageUrl = completedTask.result.photoUrl.toString()

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
