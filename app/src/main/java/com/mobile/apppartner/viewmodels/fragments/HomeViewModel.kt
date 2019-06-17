package com.mobile.apppartner.viewmodels.fragments

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.mobile.apppartner.models.Interes
import com.mobile.apppartner.models.UserDatabase
import com.mobile.apppartner.models.api.firebase.ApiFirebase
import com.mobile.apppartner.views.adapter.InteresAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*

class HomeViewModel:ViewModel() {

    var interesArray:MutableList<Interes> = mutableListOf()
    lateinit var currentFragment:Fragment
    var userDatabase: UserDatabase?=null

    fun prueba(fragment:Fragment){
        this.currentFragment=fragment
        currentFragment.rvInteresesHO.setHasFixedSize(true)
        val layout = LinearLayoutManager(currentFragment.context)
        layout.orientation = LinearLayoutManager.HORIZONTAL
        currentFragment.rvInteresesHO.setAdapter(InteresAdapter(interesArray))
        currentFragment.rvInteresesHO.setLayoutManager(layout)
    }

    fun getInteres():Observable<MutableList<Interes>>{
        val apiFirebase = ApiFirebase()
        return apiFirebase.getInteres()
    }

    fun getInfoCurrentUser():Observable<UserDatabase>{
        val apiFirebase = ApiFirebase()
        return apiFirebase.getInfoCurrentUser()
    }

    fun getRamdonUser():Observable<MutableList<UserDatabase>>{
        val apiFirebase = ApiFirebase()
        return apiFirebase.getRamdonUser(userDatabase!!.campus)
    }
}