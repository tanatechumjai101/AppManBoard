package com.example.AppManBoard.login.viewmodel

import android.arch.lifecycle.ViewModel

class LoginViewMedel : ViewModel(){

    fun checkLogin(email: String, password: String):Boolean{
        return email=="tanate.chu@appman.co.th" && password == "1234"
    }
}