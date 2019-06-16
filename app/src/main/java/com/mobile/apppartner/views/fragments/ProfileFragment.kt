package com.example.appprueba

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.apppartner.Components.CircleTransformation
import com.mobile.apppartner.models.UserDatabase
import com.mobile.apppartner.R
import com.mobile.apppartner.viewmodels.fragments.ProfileViewModel
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: Fragment() {

    lateinit var viewModel: ProfileViewModel
    var us:UserDatabase?=null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getInfoCurrentUser(this)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            us=it
            setValues()
        },{
            print(it.toString())
        })//new commit
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)





        this.btnSalir.setOnClickListener {
            viewModel.signOut(activity!!)
        }
    }
    fun setValues(){
        this.txtNombrePR.setText(us?.fullname.toString())
        this.txtDescripcionPR.setText(us?.descripcion.toString())
        Picasso.get().load(us?.url_img.toString()).transform(CircleTransformation()).into(this.imgPerfilPR)
    }
}