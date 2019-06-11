package com.mobile.apppartner.interfaces

import com.google.firebase.database.DataSnapshot

interface FirebaseCallBacks {
    fun onNewMessage(dataSnapshot: DataSnapshot)
}