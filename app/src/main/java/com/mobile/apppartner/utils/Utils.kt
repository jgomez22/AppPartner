package com.mobile.apppartner.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    var MESSAGE_AUTHENTICATION_FAILED = "Firebase authentication failed, please check your internet connection"
    var MESSAGE_INVALIDE_ROOM_NAME = "Enter a valid Name"

    var EXTRA_ROOM_NAME = "EXTRA_ROOM_NAME"

    val OPEN_ACTIVITY = 1
    val SHOW_TOAST = 2
    val UPDATE_MESSAGES = 1

    fun convertTime(timestamp: Long): String {
        val sdf: SimpleDateFormat
        sdf = SimpleDateFormat("HH:mm")
        val date = Date(timestamp)
        sdf.setTimeZone(TimeZone.getDefault())
        return sdf.format(date)
    }
}
