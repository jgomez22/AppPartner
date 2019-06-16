package com.mobile.apppartner.ui.chat

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mobile.apppartner.models.UserMatch

class UserMatchViewModel : ViewModel()  {
    private val id = MutableLiveData<String>()
    private val name = MutableLiveData<String>()

    fun bind(user: UserMatch) {
        id.value = user.id
        name.value = user.name
    }

    fun getId(): MutableLiveData<String> {
        return id
    }

    fun getName(): MutableLiveData<String> {
        return name
    }
}