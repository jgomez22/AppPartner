package com.mobile.apppartner.models

data class Message(
    val recipientId: String? = null,
    var senderId: String? = null,
    val time: String? = null,
    var message: String? = null,
    var isMine: Boolean = false
)