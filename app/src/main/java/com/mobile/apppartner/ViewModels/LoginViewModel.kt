package com.mobile.apppartner.ViewModels

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.microsoft.aad.adal.AuthenticationCallback
import com.microsoft.aad.adal.AuthenticationResult
import com.mobile.apppartner.ApiOffice365.AuthenticationManager
import com.mobile.apppartner.ApiOffice365.Constants
import com.mobile.apppartner.Views.RegisterActivity
import com.mobile.apppartner.Models.ApiClient
import com.mobile.apppartner.Models.UserPartner
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*
import java.net.URI
import java.util.*

class LoginViewModel:ViewModel() {

    lateinit var apiClient:ApiClient
    lateinit var activity:Activity

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

    fun onConnectButtonClick(act:Activity){
        this.activity = act
        showConnectingInProgressUI()
        //check that client id and redirect have been set correctly
        try {
            UUID.fromString(Constants.CLIENT_ID)
            URI.create(Constants.REDIRECT_URI)
        } catch (e: IllegalArgumentException) {
            Toast.makeText(
                activity.applicationContext, "Ocurrio un error", Toast.LENGTH_LONG
            ).show()
            resetUIForConnect()
            return
        }

        AuthenticationManager.getInstance().setContextActivity(activity)
        AuthenticationManager.getInstance().connect(
            object : AuthenticationCallback<AuthenticationResult> {
                /**
                 * If the connection is successful, the activity extracts the username and
                 * displayableId values from the authentication result object and sends them
                 * to the SendMail activity.
                 * @param result The authentication result object that contains information about
                 * the user and the tokens.
                 */
                override fun onSuccess(result: AuthenticationResult) {
                    goToRegister(result.userInfo.displayableId.toString(),
                        result.userInfo.givenName.toString()+" "+result.userInfo.familyName.toString(),
                        activity)

                    resetUIForConnect()
                }

                override fun onError(e: Exception) {

                    showConnectErrorUI()
                }
            })
    }

    private fun resetUIForConnect() {
        activity.pbCargar.setVisibility(View.GONE)
    }

    private fun showConnectingInProgressUI() {
        activity.pbCargar.setVisibility(View.VISIBLE)
    }

    private fun showConnectErrorUI() {
        activity.pbCargar.setVisibility(View.GONE)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        AuthenticationManager
            .getInstance()
            .getAuthenticationContext()
            .onActivityResult(requestCode, resultCode, data)
    }
}