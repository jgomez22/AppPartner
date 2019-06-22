package com.mobile.apppartner.models.firebase

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
    lateinit var uid: String
    var interesArray: MutableList<Interes> = mutableListOf()
    var days: Days = Days()

    constructor() {
        this.refFData = FirebaseDatabase.getInstance()
        this.fireAuth = FirebaseAuth.getInstance()
    }

    fun getInfoCurrentUser(): Observable<UserDatabase> {
        val userObservable: Observable<UserDatabase> = Observable.create { observer ->
            uid = fireAuth.uid!!
            refFData.getReference("users").child(uid!!).addListenerForSingleValueEvent(object : ValueEventListener {
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
        val observable: Observable<MutableList<Interes>> = Observable.create { observer ->
            refFData.getReference("interes").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    if (p0.exists()) {
                        for (u in p0.children) {
                            interesArray.add(Interes(u.key.toString(), u.value.toString()))
                        }
                    }
                    observer.onNext(interesArray)
                    observer.onComplete()
                }

                override fun onCancelled(p0: DatabaseError) {
                    val error = Throwable("Hubo un error cargando los intereses: " + p0.message)
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