package com.example.AppManBoard.main.owner.viewmodel

import android.arch.lifecycle.ViewModel

class SearchModelPageOwner : ViewModel(){
    fun checkData(text: String):Boolean{
        return text=="hi nage"
    }
}