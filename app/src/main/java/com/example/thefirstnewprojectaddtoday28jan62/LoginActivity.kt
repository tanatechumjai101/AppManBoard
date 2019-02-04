package com.example.thefirstnewprojectaddtoday28jan62

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login.*


const val RC_SIGN_IN = 123

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        sign_in_button.visibility = View.VISIBLE
//        sign_out_btn.visibility = View.GONE
        sign_in_button.setSize(SignInButton.SIZE_WIDE)
        sign_in_button.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this@LoginActivity)
            .setTitle("Are you sure ?")
            .setMessage("Do you want to close the app?")
            .setPositiveButton("yes"){dialog, which -> finishAffinity() }
            .setNegativeButton("no"){dialog, which ->  }
            .show()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            sign_in_button.visibility = View.GONE
//            Handler().postDelayed({
//                sign_out_btn.visibility = View.VISIBLE
//            }, 2000)
        } catch (e: ApiException) {
//            Handler().postDelayed({
//                sign_out_btn.visibility = View.VISIBLE
//            }, 2000)
            sign_in_button.visibility = View.GONE
        }
    }
}
