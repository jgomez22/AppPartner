package com.mobile.apppartner.ViewModels

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import com.mobile.apppartner.Views.RegisterActivity
import com.mobile.apppartner.Models.ApiClient
import com.mobile.apppartner.Models.UserPartner
import io.reactivex.Observable

class LoginViewModel:ViewModel() {

    lateinit var apiClient:ApiClient

    fun goToRegister(context:Context){
        val intent = Intent(context, RegisterActivity::class.java)
        context.startActivity(intent)
    }

    fun logInViewModel(email:String,password:String,activity:Activity):Observable<UserPartner>{
        this.apiClient = ApiClient(activity)
        return apiClient.singIn(email,password)
    }
}