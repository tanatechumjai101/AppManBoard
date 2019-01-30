package com.example.thefirstnewprojectaddtoday28jan62

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
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
//        val signInIntent = mGoogleSignInClient.signInIntent
        sign_in_button.visibility = View.VISIBLE
        sign_in_button.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Are you sure exit Application?")
        builder.setMessage("Do you want to close the app?")
        builder.setPositiveButton("yes") { dialog: DialogInterface?, which: Int ->
            finish()
        }
        builder.setNegativeButton("no") { dialog: DialogInterface, i: Int->}
        builder.show()

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
        } catch (e: ApiException) {
            sign_in_button.visibility = View.GONE
        }
    }
}
