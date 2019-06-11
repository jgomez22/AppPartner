package com.mobile.apppartner.interfaces


interface Observer<T> {
    fun onObserve(event: Int, eventMessage: T)
}