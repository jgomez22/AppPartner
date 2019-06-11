package com.mobile.apppartner.models

import com.google.firebase.database.DataSnapshot
import com.mobile.apppartner.interfaces.ModelCallBacks

class MessageModel {
    private var mMessages: ArrayList<ChatPojo>? = null

    fun addMessages(dataSnapshot: DataSnapshot, callBacks: ModelCallBacks) {
        if (mMessages == null) {
            mMessages = ArrayList()
        }
        val chatPojo = ChatPojo(dataSnapshot)
        mMessages!!.add(chatPojo)
        callBacks.onModelUpdated(mMessages!!)
    }
}
