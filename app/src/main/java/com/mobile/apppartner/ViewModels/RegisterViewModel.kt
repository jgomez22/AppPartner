package com.mobile.apppartner.ViewModels

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.mobile.apppartner.Models.ApiClient
import com.mobile.apppartner.Models.UserPartner
import io.reactivex.Observable

class RegisterViewModel:ViewModel() {

    lateinit var apiClient:ApiClient
    fun backToLogin(context:Context){
        context.applicationContext
        if (context is Activity) {
            context.onBackPressed()
        }
    }

    fun createAccount(email:String,password:String,activity: Activity):Observable<UserPartner>{
        this.apiClient = ApiClient(activity)
        return apiClient.createUser(email,password)
    }
}