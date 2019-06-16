package com.mobile.apppartner.models

data class Match(
    val id: String? = null,
    val interested: UserMatch? = null,
    var to: UserMatch? = null,
    val accepted: Boolean = false
)
