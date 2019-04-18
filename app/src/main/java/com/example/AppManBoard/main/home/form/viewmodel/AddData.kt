package com.example.AppManBoard.main.home.form.viewmodel

import android.arch.lifecycle.ViewModel

class AddData: ViewModel(){

    fun checkData(detail:String,displayName:String,email:String,id:String,imageURL:String,subject: String,time:String):Boolean{
        return detail=="hi" && displayName=="tanate chumjai" && email=="tanate.chu@appman.co.th" && id =="tanate.chu@appman.co.th 155559562861"&&imageURL=="https://lh3.googleusercontent.com/a-/AAuE7mA1JtiOfRPbEpfUbGxh3GYmL33Bvd3N38VfkLqctA" && subject=="hey"&&time=="18-เม.ย.-2019-10:52:42"
    }
}