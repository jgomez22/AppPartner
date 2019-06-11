package com.mobile.apppartner.models

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.mobile.apppartner.utils.Utils

class ChatPojo(dataSnapshot: DataSnapshot) : BaseObservable() {
    var msgKey: String? = null
    var timeStamp: String? = null
    @get:Bindable
    var message: String? = null
    var isMine: Boolean = false


    init {
        val hash = dataSnapshot.getValue() as HashMap<String, Object>
        this.msgKey = dataSnapshot.getKey()
        this.message = hash.get("text").toString()
        if (hash.get("senderId").toString().equals(FirebaseAuth.getInstance().getCurrentUser()!!.getUid())) {
            isMine = true
        }
        this.timeStamp = Utils.convertTime(hash.get("time").toString().toLong())

    }
}