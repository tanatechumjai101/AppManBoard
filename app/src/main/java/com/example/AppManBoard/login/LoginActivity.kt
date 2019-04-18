package com.example.AppManBoard.login

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import com.example.AppManBoard.R
import com.example.AppManBoard.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.common.api.ApiException
import android.util.Log
import android.widget.Toast
import com.example.AppManBoard.login.viewmodel.LoginViewMedel


const val RC_SIGN_IN = 123

class LoginActivity : AppCompatActivity() {

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var vm: LoginViewMedel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        vm = ViewModelProviders.of(this).get(LoginViewMedel::class.java)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        btn_signin.setSize(SignInButton.SIZE_WIDE)

        btn_signin.setOnClickListener {

            var x = vm.checkLogin("test@gmail.com","1234")

            val signInIntent = mGoogleSignInClient?.signInIntent

            if(x){
                Toast.makeText(this@LoginActivity,"Welcome",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@LoginActivity,"Login Fail",Toast.LENGTH_SHORT).show()

            }
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
                Log.e("error", e.toString())
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {



        try {
            val account = completedTask.getResult(ApiException::class.java)
            val sharedPreference = getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()

            if (account!!.email!!.contains("@appman.co.th")) {
                try {
                    putData(editor, account)
                    startMainPage()
                } catch (e: ApiException) {
                    Toast.makeText(this@LoginActivity, "reconnect.", Toast.LENGTH_SHORT).show()
                    putData(editor, account)
                    startMainPage()
                }
            } else {
                if (mGoogleSignInClient != null) {
                    Toast.makeText(this@LoginActivity, "Please reconnect.", Toast.LENGTH_SHORT).show()
                    mGoogleSignInClient!!.signOut()
                }
            }

        } catch (e: ApiException) {
            Log.e("login", "signInResult:failed code=" + e.statusCode)
        }


    }

    private fun startMainPage() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    private fun putData(editor: SharedPreferences.Editor, account: GoogleSignInAccount?) {

        editor.putString("display_name", account?.displayName).apply()
        editor.putString("email", account?.email).apply()
        editor.putString("img_url", account?.photoUrl.toString()).apply()

    }
}
