package com.mobile.apppartner.views

import android.arch.lifecycle.ViewModelProviders
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.mobile.apppartner.R
import com.mobile.apppartner.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        var bottomNav: BottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_1)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        viewModel.inicializar(this)

    }
    private var navListener: BottomNavigationView.OnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener {
        viewModel.changeFragment(it)
        true
    }
}
