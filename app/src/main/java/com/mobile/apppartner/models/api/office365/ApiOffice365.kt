package com.mobile.apppartner.models.api.office365

import android.app.Activity
import android.view.View
import com.microsoft.aad.adal.AuthenticationCallback
import com.microsoft.aad.adal.AuthenticationResult
import com.mobile.apppartner.ApiOffice365.AuthenticationManager
import com.mobile.apppartner.ApiOffice365.Constants
import com.mobile.apppartner.models.UserVal
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*
import java.net.URI
import java.util.*

class ApiOffice365 {

    lateinit var currentActivity:Activity

    fun signInOffice365(activity:Activity): Observable<UserVal> {
        currentActivity=activity
        val observable: Observable<UserVal> = Observable.create { observer ->
            showConnectingInProgressUI()
            UUID.fromString(Constants.CLIENT_ID)
            URI.create(Constants.REDIRECT_URI)

            AuthenticationManager.getInstance().setContextActivity(this.currentActivity)
            AuthenticationManager.getInstance().connect(
                object : AuthenticationCallback<AuthenticationResult> {
                    override fun onSuccess(result: AuthenticationResult) {
                        observer.onNext(UserVal(result.userInfo.displayableId.toString(),result.userInfo.givenName.toString()+" "+result.userInfo.familyName.toString()))
                        resetUIForConnect()
                    }

                    override fun onError(e: Exception) {

                        showConnectErrorUI()
                    }
                })

        }

        return observable
    }

    private fun resetUIForConnect() {
        this.currentActivity.pbCargar.setVisibility(View.GONE)
    }

    private fun showConnectingInProgressUI() {
        this.currentActivity.pbCargar.setVisibility(View.VISIBLE)
    }

    private fun showConnectErrorUI() {
        this.currentActivity.pbCargar.setVisibility(View.GONE)
    }
}