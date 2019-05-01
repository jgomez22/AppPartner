package com.example.appprueba

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.mobile.apppartner.R
import com.mobile.apppartner.Views.LoginActivity
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.btnSalir.setOnClickListener {
            FirebaseAuth.getInstance().signOut();
            startActivity(Intent(this.context,LoginActivity::class.java))
            activity?.finish()
        }
    }
}