package com.mobile.apppartner.ViewModels

import android.app.Activity
import android.arch.lifecycle.ViewModel
import com.mobile.apppartner.Models.ApiClient
import com.mobile.apppartner.Models.UserPartner
import io.reactivex.Observable

class RegisterViewModel:ViewModel() {

    lateinit var apiClient:ApiClient
    fun createAccount(email:String,password:String,activity: Activity):Observable<UserPartner>{
        this.apiClient = ApiClient(activity)
        return apiClient.createUser(email,password)
    }
}