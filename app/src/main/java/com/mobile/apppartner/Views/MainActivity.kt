package com.mobile.apppartner.Views

import android.arch.lifecycle.ViewModelProviders
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import com.example.appprueba.ChatFragment
import com.example.appprueba.HomeFragment
import com.example.appprueba.ProfileFragment
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG_ONE = "home"
private const val TAG_SECOND = "chat"
private const val TAG_THIRD = "profile"
private const val MAX_HISTORIC = 4

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    private var currentFragment: Fragment? = null

    private val listState = mutableListOf<StateFragment>()
    private var currentTag: String = TAG_ONE
    private var oldTag: String = TAG_ONE
    private var currentMenuItemId: Int = R.id.nav_home

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) loadFirstFragment()
        init()

        supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)


    }

    private fun init() {

        bottom_navigation_1.setOnNavigationItemSelectedListener { menuItem ->

            if (currentMenuItemId != menuItem.itemId) {

                val fragment: Fragment
                oldTag = currentTag

                currentMenuItemId = menuItem.itemId

                when (currentMenuItemId) {
                    R.id.nav_home -> {
                        currentTag = TAG_ONE
                        fragment = HomeFragment.newInstance()
                        loadFragment(fragment, currentTag)
                    }
                    R.id.nav_chat -> {
                        currentTag = TAG_SECOND
                        fragment = ChatFragment.newInstance()
                        loadFragment(fragment, currentTag)
                    }
                    R.id.nav_profile -> {
                        currentTag = TAG_THIRD
                        fragment = ProfileFragment.newInstance()
                        loadFragment(fragment, currentTag)
                    }
                }

                return@setOnNavigationItemSelectedListener true

            }

            false
        }

    }

    override fun onBackPressed() {

        if (listState.size >= 1) {
            recoverFragment()
        } else {
            super.onBackPressed()
        }

    }

    private fun recoverFragment() {

        val lastState = listState.last()
        listState.removeAt(listState.size - 1)

        currentTag = lastState.currentFragmentTag
        oldTag = lastState.oldFragmentTag

        Log.d("thr recover", "$currentTag - $oldTag")

        val ft = supportFragmentManager.beginTransaction()

        val currentFragment = supportFragmentManager.findFragmentByTag(currentTag)
        val oldFragment = supportFragmentManager.findFragmentByTag(oldTag)

        if (currentFragment!!.isVisible && oldFragment!!.isHidden) {
            ft.hide(currentFragment!!).show(oldFragment!!)
        }

        ft.commit()

        val menu = bottom_navigation_1.menu

        when (oldTag) {
            TAG_ONE -> menu.getItem(0).isChecked = true
            TAG_SECOND -> menu.getItem(1).isChecked = true
            TAG_THIRD -> menu.getItem(2).isChecked = true
        }

    }

    private fun loadFirstFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        currentFragment = HomeFragment.newInstance()
        transaction.add(R.id.fragment_container, currentFragment!!, TAG_ONE)
        transaction.commit()
    }

    private fun loadFragment(fragment: Fragment, tag: String) {

        if (currentFragment !== fragment) {
            val ft = supportFragmentManager.beginTransaction()

            if (fragment.isAdded) {
                ft.hide(currentFragment!!).show(fragment)
            } else {
                ft.hide(currentFragment!!).add(R.id.fragment_container, fragment, tag)
            }
            currentFragment = fragment

            ft.commit()

            addBackStack()
        }

    }

    //Like YouTube
    private fun addBackStack() {
        Log.d("thr add", "$currentTag - $oldTag")

        when (listState.size) {
            MAX_HISTORIC -> {

                listState[1].oldFragmentTag = TAG_ONE
                val firstState = listState[1]

                for (i in listState.indices) {
                    if (listState.indices.contains((i + 1))) {
                        listState[i] = listState[i + 1]
                    }
                }

                listState[0] = firstState
                listState[listState.lastIndex] = StateFragment(currentTag, oldTag)
            }
            else -> {
                listState.add(StateFragment(currentTag, oldTag))
            }
        }

    }
}

data class StateFragment(val currentFragmentTag: String, var oldFragmentTag: String)
