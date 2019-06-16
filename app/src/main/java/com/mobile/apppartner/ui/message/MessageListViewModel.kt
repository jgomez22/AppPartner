package com.mobile.apppartner.ui.message

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.models.Message
import com.mobile.apppartner.models.UserDatabase

import io.reactivex.Observable

import io.reactivex.disposables.Disposable
import java.util.*

class MessageListViewModel : ViewModel() {

    lateinit var ref: DatabaseReference
    val messageListAdapter: MessageListAdapter = MessageListAdapter()
    private lateinit var subscription: Disposable

    init {
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadPosts(){
        val user: Observable<UserDatabase> = Observable.create { observer ->

            val uid = FirebaseAuth.getInstance().uid
            ref = FirebaseDatabase.getInstance().getReference("messages").child(uid!!)

            ref.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    Log.d("TAG", p0.toString())

                    val messageList: List<Message> = listOf(Message("1", "1234", Date(1234), "Hola amigos"))

                    messageListAdapter.updateMessageList(messageList)
                }

                override fun onCancelled(p0: DatabaseError) {
                    print(p0.message)
                }
            })
        }
        subscription = user.subscribe();
    }

}