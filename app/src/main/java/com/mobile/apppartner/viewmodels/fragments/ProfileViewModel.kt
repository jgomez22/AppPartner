package com.mobile.apppartner.viewmodels.fragments

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.example.appprueba.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.models.Match
import com.mobile.apppartner.models.UserDatabase
import com.mobile.apppartner.models.firebase.ApiFirebase
import com.mobile.apppartner.views.LoginActivity
import io.reactivex.Observable

class ProfileViewModel:ViewModel() {

    lateinit var ref: DatabaseReference
    var u: UserDatabase? = null

    fun signOut(activity:FragmentActivity){
        FirebaseAuth.getInstance().signOut();
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    fun getInfoCurrentUser(activity: ProfileFragment):Observable<UserDatabase>{
        val apiFirebase =ApiFirebase()
        return apiFirebase.getInfoCurrentUser()
    }

    fun getDataReport(activity: ProfileFragment):Observable<Match>{
        val apiFirebase =ApiFirebase()
        return apiFirebase.getDataReport()
    }

}