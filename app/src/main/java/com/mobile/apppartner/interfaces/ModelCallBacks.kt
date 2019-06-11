package com.mobile.apppartner.interfaces

import com.mobile.apppartner.models.ChatPojo
import java.util.ArrayList

interface ModelCallBacks {
    fun onModelUpdated(messages: ArrayList<ChatPojo>)
}
