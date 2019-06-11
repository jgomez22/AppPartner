package com.mobile.apppartner.models

public class Chat {
     var msgKey: String? = null
     var timeStamp: String? = null
     var message: String? = null
     var isMine: Boolean = false

    constructor(msgKey:String,timeStamp:String,message:String,isMine:Boolean){
        this.msgKey=msgKey
        this.timeStamp=timeStamp
        this.message=message
        this.isMine=isMine
    }

}