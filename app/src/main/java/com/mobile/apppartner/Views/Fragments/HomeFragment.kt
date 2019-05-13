package com.example.appprueba

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import com.mobile.apppartner.Models.Interes
import kotlinx.android.synthetic.main.fragment_home.view.*
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.HomeViewModel

class HomeFragment: Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var ref: DatabaseReference
    var asd:MutableList<Interes> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home,container,false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("interes")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (u in p0.children){
                        asd.add(Interes(u.key.toString(),u.value.toString()))
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                print(p0.message)
                }
        })
    }
}