package com.mobile.apppartner.models

class UserPartner {

    lateinit var email:String
    val isVerified:Boolean

    constructor(email:String,isVerified:Boolean){
        this.isVerified = isVerified
        this.email = email
    }
}