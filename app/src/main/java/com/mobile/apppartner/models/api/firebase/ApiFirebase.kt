package com.mobile.apppartner.models.api.firebase

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.models.*
import io.reactivex.Observable
import java.time.LocalDate
import java.time.LocalDateTime

class ApiFirebase {
    var fireAuth: FirebaseAuth
    var refFData: FirebaseDatabase
    var userDatabase: UserDatabase? = null
    lateinit var uid:String
    var interesArray:MutableList<Interes> = mutableListOf()
    var days: Days = Days()


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

    fun getRamdonUserWithInterest(campus:String):Observable<MutableList<UserDatabase>>{
        val userObservable:Observable<MutableList<UserDatabase>> = Observable.create{observer->

        }
        return userObservable
    }

    fun getRamdonUser(campus:String):Observable<MutableList<UserDatabase>>{
        var arrayUser:MutableList<UserDatabase> = mutableListOf()

        val userObservable:Observable<MutableList<UserDatabase>> = Observable.create{observer->
            uid = fireAuth.uid!!

            val a = refFData.getReference("users/")
            val query:Query = a.orderByChild("campus").equalTo(campus)
            query.addListenerForSingleValueEvent(object:ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }
                override fun onDataChange(p0: DataSnapshot) {
                    var u = UserDatabase()

                    if(p0.exists() && p0.childrenCount.toInt()!=1){
                        for(us in p0.children){
                            u = us.getValue(UserDatabase::class.java)!!
                            if(!u.uid.equals(fireAuth.uid.toString()))arrayUser.add(u)
                        }
                        observer.onNext(arrayUser)
                    } else {
                        val error = Throwable("No se encontraron resultados")
                        observer.onError(error)
                    }
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

    fun getDataReport(): Observable<Days> {
        val data: Observable<Days> = Observable.create { observer ->
            uid = fireAuth.uid!!
            refFData.getReference("match").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val day = Days()
                    for (data in dataSnapshot.children) {
                        val value: Match? = data.getValue(Match::class.java)
                        if (value!!.to!!.id.equals(uid)) {
                            var creationTime: LocalDate = LocalDateTime.parse(value!!.time).toLocalDate()
                            var today = LocalDate.now()
                            if (creationTime.equals(today.minusDays(6))) {
                                day.count6 = day.count6!!.plus(1)
                            } else if (creationTime.equals(today.minusDays(5))) {
                                day.count5 = day.count5!!.plus(1)
                            } else if (creationTime.equals(today.minusDays(4))) {
                                day.count4 = day.count4!!.plus(1)
                            } else if (creationTime.equals(today.minusDays(3))) {
                                day.count3 = day.count3!!.plus(1)
                            } else if (creationTime.equals(today.minusDays(2))) {
                                day.count2 = day.count2!!.plus(1)
                            } else if (creationTime.equals(today.minusDays(1))) {
                                day.count1 = day.count1!!.plus(1)
                            } else if (creationTime.equals(today)) {
                                day.count0 = day.count0!!.plus(1)
                            }
                        }
                    }
                    days = day
                    observer.onNext(days)
                    observer.onComplete()
                }

                override fun onCancelled(p0: DatabaseError) {
                    print(p0.message)
                }
            })
        }
        return data
    }
}