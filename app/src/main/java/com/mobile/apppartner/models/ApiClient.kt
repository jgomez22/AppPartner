package com.mobile.apppartner.models

import android.app.Activity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.microsoft.aad.adal.AuthenticationCallback
import com.microsoft.aad.adal.AuthenticationResult
import com.mobile.apppartner.ApiOffice365.AuthenticationManager
import com.mobile.apppartner.ApiOffice365.Constants

import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_login.*
import java.net.URI
import java.util.*

class ApiClient {

    constructor(activity:Activity){
        this.auth = FirebaseAuth.getInstance()
        this.currentActivity = activity
    }
    private lateinit var currentActivity:Activity
    private lateinit var auth: FirebaseAuth

    fun createUser(email:String,password:String):Observable<UserPartner>{
        val observable:Observable<UserPartner> = Observable.create { observer ->
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this.currentActivity){
                task->
                if(task.isSuccessful){
                    //this.auth.currentUser?.sendEmailVerification()
                    var email:String? = this.auth.currentUser?.email
                    var isEmailVerified:Boolean? = this.auth.currentUser?.isEmailVerified
                    if(email!= null && isEmailVerified!=null){
                        val meUser = UserPartner(email,isEmailVerified)
                        observer.onNext(meUser)
                        observer.onComplete()
                    }
                } else {
                    val error:Throwable = Throwable("No se pudo crear la cuenta")
                    observer.onError(error)
                }
            }
        }
        return observable
    }

    fun singIn(email:String,password:String):Observable<UserPartner>{
        val observable:Observable<UserPartner> = Observable.create { observer ->
            this.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this.currentActivity){
                if(it.isSuccessful){
                    var email:String? = this.auth.currentUser?.email
                    var isEmailVerified:Boolean? = this.auth.currentUser?.isEmailVerified
                    if(email!=null && isEmailVerified!=null){
                        val meUser = UserPartner(email,isEmailVerified)
                        observer.onNext(meUser)
                        observer.onComplete()
                    }
                } else {
                    val error:Throwable = Throwable("el email y password son incorrectos",null)
                    observer.onError(error)
                }
            }
        }
        return observable
    }

    fun signInOffice365():Observable<UserVal>{
        val observable:Observable<UserVal> = Observable.create { observer ->
            showConnectingInProgressUI()
            //check that client id and redirect have been set correctly

                UUID.fromString(Constants.CLIENT_ID)
                URI.create(Constants.REDIRECT_URI)

            AuthenticationManager.getInstance().setContextActivity(this.currentActivity)
            AuthenticationManager.getInstance().connect(
                object : AuthenticationCallback<AuthenticationResult> {
                    override fun onSuccess(result: AuthenticationResult) {

                        //goToRegister(result.userInfo.displayableId.toString(),
                          //  result.userInfo.givenName.toString()+" "+result.userInfo.familyName.toString(),
                            //activity)
                        observer.onNext(UserVal(result.userInfo.displayableId.toString(),result.userInfo.givenName.toString()+" "+result.userInfo.familyName.toString()))
                        resetUIForConnect()
                        //observer.onComplete()
                    }

                    override fun onError(e: Exception) {

                        showConnectErrorUI()
                    }
                })

        }

        return observable
    }

    fun sendMessage(email:String,password:String):Observable<UserPartner>{
        val observable:Observable<UserPartner> = Observable.create { observer ->
            this.auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this.currentActivity){
                if(it.isSuccessful){
                    var email:String? = this.auth.currentUser?.email
                    var isEmailVerified:Boolean? = this.auth.currentUser?.isEmailVerified
                    if(email!=null && isEmailVerified!=null){
                        val meUser = UserPartner(email,isEmailVerified)
                        observer.onNext(meUser)
                        observer.onComplete()
                    }
                } else {
                    val error:Throwable = Throwable("el email y password son incorrectos",null)
                    observer.onError(error)
                }
            }
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

public class UserVal(val email:String,val fullname:String)