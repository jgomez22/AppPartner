package com.mobile.apppartner.models

import java.util.*

data class Message(
    val recipientId: String? = null,
    var senderId: String? = null,
    val time: Date? = null,
    var message: String? = null,
    var isMine: Boolean = false
)