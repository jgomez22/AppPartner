package com.mobile.apppartner.Models

class UserPartner {

    lateinit var email:String
    val isVerified:Boolean

    constructor(email:String,isVerified:Boolean){
        this.isVerified = isVerified
        this.email = email
    }
}