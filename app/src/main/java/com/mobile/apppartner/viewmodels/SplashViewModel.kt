package com.mobile.apppartner.viewmodels

import android.arch.lifecycle.ViewModel
import android.content.Intent
import java.util.*
import kotlin.concurrent.schedule
import com.mobile.apppartner.views.LoginActivity
import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.mobile.apppartner.views.MainActivity


class SplashViewModel:ViewModel() {

    fun goTo(activity: Activity){
        Timer().schedule(3000){

            val user = FirebaseAuth.getInstance().currentUser
            lateinit var intent:Intent
            if (user != null) {
                intent = Intent(activity,MainActivity::class.java)
            } else {
                intent = Intent(activity,LoginActivity::class.java)
            }
            activity.startActivity(intent)
            activity.finish()
        }
    }
}