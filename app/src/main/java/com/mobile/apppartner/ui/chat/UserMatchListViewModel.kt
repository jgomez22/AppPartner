package com.mobile.apppartner.ui.chat

import android.arch.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.models.Match
import com.mobile.apppartner.models.UserDatabase
import com.mobile.apppartner.models.UserMatch
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlin.collections.ArrayList

class UserMatchListViewModel : ViewModel() {

    lateinit var ref: DatabaseReference
    val userMatchListAdapter: UserMatchListAdapter = UserMatchListAdapter()
    private lateinit var subscription: Disposable

    init {
        loadUserMatchList()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadUserMatchList() {
        val user: Observable<UserDatabase> = Observable.create { observer ->

            val uid = FirebaseAuth.getInstance().uid
            ref = FirebaseDatabase.getInstance().getReference("match")

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var list: List<UserMatch?> = ArrayList<UserMatch>()
                    for (data in dataSnapshot.children) {
                        val value: Match? = data.getValue(Match::class.java)
                        if (value!!.interested!!.id.equals(uid)) {
                            if (value!!.accepted) {
                                list += value!!.to
                            }
                        }else if (value!!.to!!.id.equals(uid)) {
                            list += value!!.interested
                        }
                    }
                    userMatchListAdapter.updateUserMatch(list)
                }

                override fun onCancelled(p0: DatabaseError) {
                    print(p0.message)
                }
            })
        }
        subscription = user.subscribe();
    }
}