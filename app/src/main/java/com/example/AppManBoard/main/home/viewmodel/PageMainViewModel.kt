package com.example.AppManBoard.main.home.viewmodel

import android.arch.lifecycle.ViewModel

class PageMainViewModel : ViewModel(){

    fun checkData(text:String):Boolean{
        return text=="hi"
    }
}