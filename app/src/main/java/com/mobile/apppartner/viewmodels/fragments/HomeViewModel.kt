package com.mobile.apppartner.viewmodels.fragments

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mobile.apppartner.Components.CircleTransformation
import com.mobile.apppartner.models.Interes
import com.mobile.apppartner.models.UserDatabase
import com.mobile.apppartner.models.api.firebase.ApiFirebase
import com.mobile.apppartner.views.adapter.InteresAdapter
import com.mobile.apppartner.views.adapter.RecyclerItemClickListenr
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*

class HomeViewModel:ViewModel() {

    var interesArray:MutableList<Interes> = mutableListOf()
    var userArray:MutableList<UserDatabase> = mutableListOf()
    var userArrayFilterInterest:MutableList<UserDatabase> = mutableListOf()
    lateinit var currentFragment:Fragment
    var userDatabase: UserDatabase?=null
    var indexOfInterest:Int?=null

    fun prueba(fragment:Fragment){
        this.currentFragment=fragment
        currentFragment.rvInteresesHO.setHasFixedSize(true)
        val layout = LinearLayoutManager(currentFragment.context)
        layout.orientation = LinearLayoutManager.HORIZONTAL
        currentFragment.rvInteresesHO.setAdapter(InteresAdapter(interesArray))
        currentFragment.rvInteresesHO.setLayoutManager(layout)

        currentFragment.rvInteresesHO
            .addOnItemTouchListener(RecyclerItemClickListenr(currentFragment.context!!,
                currentFragment.rvInteresesHO,
                object :RecyclerItemClickListenr.OnItemClickListener{
                    override fun onItemClick(view: View, position: Int) {
                        indexOfInterest = position
                        getRamdonUserWithInterest()
                    }

                    override fun onItemLongClick(view: View?, position: Int) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                }))
        /*
        recyclerView.addOnItemTouchListener(RecyclerItemClickListenr(this, recyclerView, object : RecyclerItemClickListenr.OnItemClickListener {

        override fun onItemClick(view: View, position: Int) {
            //do your work here..
        }
        override fun onItemLongClick(view: View?, position: Int) {
            TODO("do nothing")
        }
    }))
         */
    }

    fun getInteres():Observable<MutableList<Interes>>{
        val apiFirebase = ApiFirebase()
        return apiFirebase.getInteres()
    }

    fun getInfoCurrentUser():Observable<UserDatabase>{
        val apiFirebase = ApiFirebase()
        return apiFirebase.getInfoCurrentUser()
    }

    fun getRamdonUser():Observable<MutableList<UserDatabase>>{
        val apiFirebase = ApiFirebase()
        return apiFirebase.getRamdonUser(userDatabase!!.campus)
    }

    fun getRamdonUserWithInterest(){
        userArrayFilterInterest = mutableListOf()
        for(a in userArray){
            for(b in a.interest){
                if(b.equals(indexOfInterest)){
                    userArrayFilterInterest.add(a)
                }
            }
        }
        print(userArrayFilterInterest.toString())
    }

    fun setUserIntoCardProfile(){
        val us = userArray.random()
        currentFragment.txtName.text = us.fullname
        currentFragment.txtAge.text = us.age
        currentFragment.txtCareer.text = us.career
        currentFragment.txtNumber.text = us.telephone
        currentFragment.txtDescr.text = us.descripcion
        Picasso.get().load(us.url_img).transform(CircleTransformation()).into(currentFragment.ivImageUser)
        //Picasso.get().load(us?.url_img.toString()).transform(CircleTransformation()).into(this.imgPerfilPR)
    }
}