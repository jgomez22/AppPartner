package com.mobile.apppartner.viewmodels

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.widget.Toast
import com.microsoft.aad.adal.AuthenticationCallback
import com.microsoft.aad.adal.AuthenticationResult
import com.mobile.apppartner.ApiOffice365.AuthenticationManager
import com.mobile.apppartner.ApiOffice365.Constants
import com.mobile.apppartner.views.RegisterActivity
import com.mobile.apppartner.models.ApiClient
import com.mobile.apppartner.models.UserPartner
import com.mobile.apppartner.models.UserVal
import com.mobile.apppartner.models.office365.ApiOffice365
import com.mobile.apppartner.views.MainActivity
import io.reactivex.Observable
import java.net.URI
import java.util.*

class LoginViewModel:ViewModel() {

    lateinit var apiClient:ApiClient
    lateinit var apiOffice365:ApiOffice365
    lateinit var activity:Activity

    fun goToRegister(correo:String,fullname:String,activity:Activity){
        AuthenticationManager.getInstance().disconnect()
        val intent = Intent(activity, RegisterActivity::class.java)
        intent.putExtra("fullname",fullname)
        intent.putExtra("correo",correo)
        activity.startActivity(intent)
    }

    fun goToMain(activity:Activity){
        activity.startActivity(Intent(activity,MainActivity::class.java))
        activity.finish()
    }

    fun logInViewModel(email:String,password:String,activity:Activity):Observable<UserPartner>{
        this.apiClient = ApiClient(activity)
        return apiClient.singIn(email,password)
    }

    fun signInOffice365(activity:Activity):Observable<UserVal>{
        this.apiOffice365 = ApiOffice365()
        return apiOffice365.signInOffice365(activity)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        AuthenticationManager
            .getInstance()
            .getAuthenticationContext()
            .onActivityResult(requestCode, resultCode, data)
    }
}