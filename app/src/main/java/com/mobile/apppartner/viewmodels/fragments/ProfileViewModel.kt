package com.mobile.apppartner.viewmodels.fragments

import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.example.appprueba.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.models.UserDatabase
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

    fun getUser(activity: ProfileFragment):Observable<UserDatabase>{
        val user:Observable<UserDatabase> = Observable.create {observer ->

            val uid = FirebaseAuth.getInstance().uid
            ref = FirebaseDatabase.getInstance().getReference("users").child(uid!!)

            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    u = p0.getValue(UserDatabase::class.java)

                    observer.onNext(u!!)
                    observer.onComplete()
                }
                override fun onCancelled(p0: DatabaseError) {
                    print(p0.message)
                }
            })

        }
        return user
    }

}