package com.mobile.apppartner.ui.message

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.app.AppCompatActivity
import com.mobile.apppartner.ui.chat.UserMatchListViewModel

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessageListViewModel::class.java)) {
            return MessageListViewModel() as T
        }
        if (modelClass.isAssignableFrom(UserMatchListViewModel::class.java)) {
            return UserMatchListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}