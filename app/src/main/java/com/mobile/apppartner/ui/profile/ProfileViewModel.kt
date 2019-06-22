package com.mobile.apppartner.ui.profile

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.example.appprueba.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.models.Days
import com.mobile.apppartner.models.UserDatabase
import com.mobile.apppartner.models.api.firebase.ApiFirebase
import com.mobile.apppartner.views.LoginActivity
import io.reactivex.Observable

class ProfileViewModel : ViewModel() {

    lateinit var ref: DatabaseReference
    var u: UserDatabase? = null
    var days: Days? = null


    fun signOut(activity: FragmentActivity) {
        FirebaseAuth.getInstance().signOut();
        activity.startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finish()
    }

    fun getInfoCurrentUser(activity: ProfileFragment): Observable<UserDatabase> {
        val apiFirebase = ApiFirebase()
        return apiFirebase.getInfoCurrentUser()
    }

    fun getDataReport(): Observable<Days> {
        val apiFirebase = ApiFirebase()
        return apiFirebase.getDataReport()
    }

}