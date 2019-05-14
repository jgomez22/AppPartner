package com.example.appprueba

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.apppartner.Components.CircleTransformation
import com.mobile.apppartner.Models.UserDatabase
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.ProfileViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: Fragment() {

    lateinit var viewModel: ProfileViewModel
    var us:UserDatabase?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)


        viewModel.getUser(this).subscribe({
            setValues()
        },{
            print(it.toString())
        })

        this.btnSalir.setOnClickListener {
            viewModel.signOut(activity!!)
        }
    }
    fun setValues(){
        this.txtNombrePR.setText(viewModel.u?.fullname.toString())
        this.txtDescripcionPR.setText(viewModel.u?.descripcion.toString())
        Picasso.get().load(viewModel.u?.url_img.toString()).transform(CircleTransformation()).into(this.imgPerfilPR)
    }
}