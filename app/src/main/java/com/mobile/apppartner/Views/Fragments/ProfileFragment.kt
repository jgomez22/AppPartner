package com.example.appprueba

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.ProfileViewModel
import com.mobile.apppartner.Views.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: Fragment() {

    lateinit var viewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        this.btnSalir.setOnClickListener {
            viewModel.signOut(activity!!)

        }
    }
}