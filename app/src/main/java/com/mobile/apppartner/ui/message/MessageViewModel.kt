package com.mobile.apppartner.ui.message

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mobile.apppartner.models.Message

class MessageViewModel : ViewModel() {

    private val recipientId = MutableLiveData<String>()
    private val senderId = MutableLiveData<String>()
    private val time = MutableLiveData<String>()
    private val messageValue = MutableLiveData<String>()
    private val isMine = MutableLiveData<Boolean>()

    fun bind(message: Message) {
        recipientId.value = message.recipientId
        senderId.value = message.senderId
        time.value = message.time
        messageValue.value = message.message
        isMine.value = message.isMine
    }

    fun getRecipientId(): MutableLiveData<String> {
        return recipientId
    }

    fun getSenderId(): MutableLiveData<String> {
        return senderId
    }

    fun getTimeId(): MutableLiveData<String> {
        return time
    }

    fun getMessageValue(): MutableLiveData<String> {
        return messageValue
    }

    fun getIsMine(): MutableLiveData<Boolean> {
        return isMine
    }
}