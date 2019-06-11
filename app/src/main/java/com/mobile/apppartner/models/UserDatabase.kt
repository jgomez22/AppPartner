package com.mobile.apppartner.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
public data class UserDatabase(
    var uid:String? = "",
    var email:String= "",
    var fullname:String= "",
    var url_img:String= "",
    var interest:MutableList<Int> = mutableListOf(),
    var career:String= "",
    var campus:String= "",
    var telephone:String= "",
    var age:String= "",
    var descripcion:String=""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "email" to email,
            "fullname" to fullname,
            "url_img" to url_img,
            "interest" to interest,
            "career" to career,
            "campus" to campus,
            "telephone" to telephone,
            "age" to age,
            "descripcion" to descripcion
        )
    }
}