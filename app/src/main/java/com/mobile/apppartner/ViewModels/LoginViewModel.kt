package com.mobile.apppartner.ViewModels

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import com.mobile.apppartner.ApiOffice365.AuthenticationManager
import com.mobile.apppartner.Views.RegisterActivity
import com.mobile.apppartner.Models.ApiClient
import com.mobile.apppartner.Models.UserPartner
import io.reactivex.Observable

class LoginViewModel:ViewModel() {

    lateinit var apiClient:ApiClient

    fun goToRegister(correo:String,fullname:String,activity:Activity){
        AuthenticationManager.getInstance().disconnect()
        val intent = Intent(activity, RegisterActivity::class.java)
        intent.putExtra("fullname",fullname)
        intent.putExtra("correo",correo)
        activity.startActivity(intent)
    }

    fun logInViewModel(email:String,password:String,activity:Activity):Observable<UserPartner>{
        this.apiClient = ApiClient(activity)
        return apiClient.singIn(email,password)
    }

}