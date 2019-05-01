package com.mobile.apppartner.ViewModels

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.mobile.apppartner.Views.LoginActivity

class ProfileViewModel:ViewModel() {

    fun signOut(activity:FragmentActivity){
        FirebaseAuth.getInstance().signOut();
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }
}