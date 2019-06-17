package com.mobile.apppartner.ui.message

import android.arch.lifecycle.ViewModel
import android.content.Intent.getIntent
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.models.Message
import com.mobile.apppartner.models.UserDatabase

import io.reactivex.Observable

import io.reactivex.disposables.Disposable
import java.time.LocalDateTime
import java.util.*

class MessageListViewModel(private val uid: String) : ViewModel() {

    lateinit var ref: DatabaseReference
    val messageListAdapter: MessageListAdapter = MessageListAdapter()
    private lateinit var subscription: Disposable

    init {
        loadMessages()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadMessages() {
        val user: Observable<UserDatabase> = Observable.create { observer ->

            val myUid = FirebaseAuth.getInstance().uid
            ref = FirebaseDatabase.getInstance().getReference("messages")

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var list: List<Message?> = ArrayList<Message>()
                    for (data in dataSnapshot.children) {
                        val value: Message? = data.getValue(Message::class.java)
                        if (value!!.recipientId.equals(myUid) && value!!.senderId.equals(uid) ||
                            value!!.recipientId.equals(uid) && value!!.senderId.equals(myUid)
                        ) {
                            list += value
                        }
                    }
                    messageListAdapter.updateMessageList(list)
                }

                override fun onCancelled(p0: DatabaseError) {
                    print(p0.message)
                }
            })
        }
        subscription = user.subscribe();
    }

    fun sendMessage(message : String) {
        val myUid = FirebaseAuth.getInstance().uid
        ref = FirebaseDatabase.getInstance().getReference("messages")
        ref.push().setValue(Message(uid, myUid, Date(), message, true))
    }
}