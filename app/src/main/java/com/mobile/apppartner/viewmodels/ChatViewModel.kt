package com.mobile.apppartner.viewmodels

import android.databinding.BaseObservable;
import com.mobile.apppartner.models.ChatPojo
import com.google.firebase.database.DataSnapshot
import com.mobile.apppartner.models.MessageModel
import com.mobile.apppartner.interfaces.FirebaseCallBacks
import com.mobile.apppartner.interfaces.ModelCallBacks
import com.mobile.apppartner.interfaces.Observer
import com.mobile.apppartner.utils.FirebaseManager
import com.mobile.apppartner.utils.Utils


class ChatViewModel(private val mRoomName: String) : BaseObservable(), FirebaseCallBacks, ModelCallBacks {

    private val mModel: MessageModel
    var observers: ArrayList<Observer<Any>>

    init {
        mModel = MessageModel()
        observers = ArrayList()
    }

    fun sendMessageToFirebase(message: String) {
        if (!message.trim().equals("")) {
            FirebaseManager.getInstance(mRoomName, this)!!.sendMessageToFirebase(message)
        }
    }

    fun setListener() {
        FirebaseManager.getInstance(mRoomName, this)!!.addMessageListeners()
    }

    fun onDestory() {
        FirebaseManager.getInstance(mRoomName, this)!!.removeListeners()
        FirebaseManager.getInstance(mRoomName, this)!!.destroy()
    }

    fun addObserver(client: Observer<Any>) {
        if (!observers.contains(client)) {
            observers.add(client)
        }
    }

    fun removeObserver(clientToRemove: Observer<Any>) {
        if (observers.contains(clientToRemove)) {
            observers.remove(clientToRemove)
        }
    }

    fun notifyObservers(eventType: Int, messages: ArrayList<ChatPojo>) {
        for (i in 0 until observers.size) {
            observers.get(i).onObserve(eventType, messages)
        }
    }

    override fun onNewMessage(dataSnapshot: DataSnapshot) {
        mModel.addMessages(dataSnapshot, this)
    }

    override fun onModelUpdated(messages: java.util.ArrayList<ChatPojo>) {
        if (messages.size > 0) {
            notifyObservers(Utils.UPDATE_MESSAGES, messages)
        }
    }

}