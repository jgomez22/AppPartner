package com.example.appprueba

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import com.mobile.apppartner.Models.Interes
import kotlinx.android.synthetic.main.fragment_home.view.*
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.HomeViewModel
import com.mobile.apppartner.Views.Adapter.InteresAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment: Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var ref: DatabaseReference
    var asd:MutableList<Interes> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home,container,false)
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeFragment()
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
                    getInteres(asd)
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                print(p0.message)
                }
        })



    }

    fun getInteres(list:MutableList<Interes>){
        this.rvInteresesHO.setHasFixedSize(true)
        val layout = LinearLayoutManager(this.context)
        layout.orientation = LinearLayoutManager.HORIZONTAL
        this.rvInteresesHO.setAdapter(InteresAdapter(list))
        this.rvInteresesHO.setLayoutManager(layout)
    }
}