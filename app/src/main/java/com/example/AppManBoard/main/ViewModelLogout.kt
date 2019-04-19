package com.example.AppManBoard.main

import android.arch.lifecycle.ViewModel

class ViewModelLogout : ViewModel(){

    fun checkEmail(email:String):Boolean{
        return email=="tanate.chu@appman.co.th"
    }
}