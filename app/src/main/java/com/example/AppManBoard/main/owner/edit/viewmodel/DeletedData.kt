package com.example.AppManBoard.main.owner.edit.viewmodel

import android.arch.lifecycle.ViewModel

class DeletedData : ViewModel(){


    fun checkData(id:String):Boolean{
        return id=="tanate.chu@appman.co.th 155559562861"
    }
}