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

}
public class UserVal(val email:String,val fullname:String)