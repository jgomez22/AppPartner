package com.mobile.apppartner.ViewModels

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Intent
import java.util.*
import kotlin.concurrent.schedule
import com.mobile.apppartner.Views.LoginActivity
import android.app.Activity



class SplashViewModel:ViewModel() {

    fun goToHome(context: Context){
        Timer().schedule(3000){
            val intent = Intent(context,LoginActivity::class.java)
            context.startActivity(intent)
            context.applicationContext
            if (context is Activity) {
                context.finish()
            }
        }
    }
}