package com.example.thefirstnewprojectaddtoday28jan62.main.person


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.thefirstnewprojectaddtoday28jan62.R
import com.example.thefirstnewprojectaddtoday28jan62.login.LoginActivity
import com.example.thefirstnewprojectaddtoday28jan62.util.Singleton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.fragment_person.*
import java.text.SimpleDateFormat
import java.util.*


class PagePersonFragment : Fragment() {
    private lateinit var mActivity: Activity
    private var googleSignIn: GoogleSignInClient? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mActivity = activity!!
        val view = inflater.inflate(R.layout.fragment_person, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreference = mActivity.getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)

        val dateTime = SimpleDateFormat("dd-MMM-yyyy-HH:mm:ss").format(Date())
        tv_last_login_profile.text = "$dateTime"

        display_profile.text = sharedPreference.getString("display_name", "")
        email_profile.text = sharedPreference.getString("email", "")
        val checking = sharedPreference.getString("img_url", "")

        if(checking == "null"){
            Glide.with(mActivity).load(R.drawable.playstore_icon).into(img_profile).view
        }else {
            Glide.with(mActivity).load(sharedPreference.getString("img_url", "")).into(img_profile)
        }

        initGoogleLogin()
        sign_out_profile.setOnClickListener {
            val sharedPreference = mActivity.getSharedPreferences("SAVE_ACCOUNT", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            googleSignIn?.signOut()
            val intent = Intent(mActivity, LoginActivity::class.java)
            startActivity(intent)
            editor.putString("email", null).apply()
            mActivity.finishAffinity()
        }
    }

    private fun initGoogleLogin() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignIn = GoogleSignIn.getClient(mActivity, googleSignInOptions)
    }

    companion object {
        fun newInstance() = PagePersonFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
