package com.mobile.apppartner.models.api.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.models.Interes
import com.mobile.apppartner.models.UserDatabase
import io.reactivex.Observable

class ApiFirebase {
    var fireAuth: FirebaseAuth
    var refFData: FirebaseDatabase
    var userDatabase: UserDatabase? = null
    lateinit var uid:String
    var interesArray:MutableList<Interes> = mutableListOf()

    constructor(){
        this.refFData = FirebaseDatabase.getInstance()
        this.fireAuth = FirebaseAuth.getInstance()
    }

    fun getInfoCurrentUser():Observable<UserDatabase>{
        val userObservable:Observable<UserDatabase> = Observable.create{observer->
            uid = fireAuth.uid!!
            refFData.getReference("users").child(uid!!).addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(p0: DataSnapshot) {
                    userDatabase = p0.getValue(UserDatabase::class.java)
                    observer.onNext(userDatabase!!)
                    observer.onComplete()
                }
                override fun onCancelled(p0: DatabaseError) {
                    print(p0.message)
                }
            })
        }
        return userObservable
    }

    fun getInteres(): Observable<MutableList<Interes>> {
        val observable:Observable<MutableList<Interes>> = Observable.create {observer->
            refFData.getReference("interes").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        for (u in p0.children){
                            interesArray.add(Interes(u.key.toString(),u.value.toString()))
                        }
                    }
                    observer.onNext(interesArray)
                    observer.onComplete()
                }
                override fun onCancelled(p0: DatabaseError) {
                    val error = Throwable("Hubo un error cargando los intereses: "+p0.message)
                    observer.onError(error)
                    print(p0.message)
                }
            })


        }
        return observable
    }
}