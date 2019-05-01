package com.mobile.apppartner.ViewModels

import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.mobile.apppartner.R
import android.view.MenuItem
import com.example.appprueba.ChatFragment
import com.example.appprueba.HomeFragment
import com.example.appprueba.ProfileFragment
import com.mobile.apppartner.Views.MainActivity

class MainViewModel:ViewModel() {

    var selectedFragment: Fragment = HomeFragment()
    lateinit var context: FragmentManager

    fun inicializar(activity: MainActivity){
        this.context = activity.supportFragmentManager
        this.context.beginTransaction().replace(R.id.fragment_container,selectedFragment).commit()
    }

    fun changeFragment(it: MenuItem){
        when(it.itemId){
            R.id.nav_home ->{
                selectedFragment = HomeFragment()
            }
            R.id.nav_chat ->{
                selectedFragment = ChatFragment()
            }
            R.id.nav_profile ->{
                selectedFragment = ProfileFragment()
            }
        }
        if (selectedFragment != null) {
            context.beginTransaction().replace(R.id.fragment_container,selectedFragment!!).commit()
        }
    }

}