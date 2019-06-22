package com.mobile.apppartner.models

import java.time.LocalDateTime

data class Match(
    val id: String? = null,
    val interested: UserMatch? = null,
    var to: UserMatch? = null,
    val accepted: Boolean = false,
    val date: LocalDateTime? = null
)
