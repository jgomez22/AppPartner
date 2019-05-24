package com.mobile.apppartner.Views

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.mobile.apppartner.R

class PopInterestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_interest)
        supportActionBar?.hide()

        val dm:DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels
        val width = dm.widthPixels
        window.setLayout((width*.8).toInt(), (height*.6).toInt())

    }
}
