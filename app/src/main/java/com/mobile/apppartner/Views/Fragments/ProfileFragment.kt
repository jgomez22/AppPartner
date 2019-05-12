package com.example.appprueba

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mobile.apppartner.Components.CircleTransformation
import com.mobile.apppartner.Models.UserDatabase
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.ProfileViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: Fragment() {

    lateinit var viewModel: ProfileViewModel
    lateinit var ref: DatabaseReference
    var us: UserDatabase? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        this.btnSalir.setOnClickListener {
            viewModel.signOut(activity!!)
        }

        val uid = FirebaseAuth.getInstance().uid
        ref = FirebaseDatabase.getInstance().getReference("users").child(uid!!)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                us = p0.getValue(UserDatabase::class.java)
                setValues(us!!)
            }

            override fun onCancelled(p0: DatabaseError) {
                print(p0.message)
            }
        })
    }

    fun setValues(u:UserDatabase){
        this.txtNombrePR.setText(u?.fullname)
        this.txtDescripcionPR.setText(u?.descripcion)
        Picasso.get().load(u?.url_img).transform(CircleTransformation()).into(this.imgPerfilPR)
    }


}