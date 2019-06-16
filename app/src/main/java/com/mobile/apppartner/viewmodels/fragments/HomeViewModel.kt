package com.mobile.apppartner.viewmodels.fragments

import android.arch.lifecycle.ViewModel
import com.mobile.apppartner.models.Interes
import com.mobile.apppartner.models.firebase.ApiFirebase
import io.reactivex.Observable

class HomeViewModel:ViewModel() {

    var interesArray:MutableList<Interes> = mutableListOf()

    fun getInteres():Observable<MutableList<Interes>>{
        val apiFirebase = ApiFirebase()
        return apiFirebase.getInteres()
    }

}