package com.mobile.apppartner.models.api.firebase

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.models.Interes
import com.mobile.apppartner.models.UserDatabase
import com.mobile.apppartner.models.UserPartner
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

    fun createUser(email:String,password:String,activity: Activity):Observable<UserPartner>{
        val observable:Observable<UserPartner> = Observable.create { observer ->
            fireAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(activity){
                    task->
                if(task.isSuccessful){
                    var email:String? = fireAuth.currentUser?.email
                    var isEmailVerified:Boolean? = fireAuth.currentUser?.isEmailVerified
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

    fun getRamdonUser(campus:String):Observable<UserDatabase>{
        val userObservable:Observable<UserDatabase> = Observable.create{observer->
            uid = fireAuth.uid!!
            val a = refFData.getReference("users/")
            val query:Query = a.orderByChild("campus").equalTo(campus)
            query.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }
                override fun onDataChange(p0: DataSnapshot) {
                    print(p0)
                    print(p0)
                }
            })
        }
        return userObservable
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