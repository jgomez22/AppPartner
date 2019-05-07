package com.mobile.apppartner.Views

import android.arch.lifecycle.ViewModelProviders
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.SplashViewModel

class SplashActivity : AppCompatActivity() {

    lateinit var viewModel:SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        //NUEVO CAMBIO JOSUE
        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.goTo(this)
    }
}
