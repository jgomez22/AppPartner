package com.example.appprueba

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import com.mobile.apppartner.models.Interes
import com.mobile.apppartner.R
import com.mobile.apppartner.viewmodels.fragments.HomeViewModel
import com.mobile.apppartner.views.adapter.InteresAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*

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

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        viewModel.getInfoCurrentUser()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                print(it)
                viewModel.userDatabase = it
                viewModel.getRamdonUser()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        print(it)
                    },{

                    })
            },{
                print(it.toString())
            })//new commit

        viewModel.getInteres()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewModel.interesArray = it
                viewModel.prueba(this)
                //getInteres()
            },{
                print(it.message)
            }
        ).isDisposed

    }

    fun getInteres(){
        this.rvInteresesHO.setHasFixedSize(true)
        val layout = LinearLayoutManager(this.context)
        layout.orientation = LinearLayoutManager.HORIZONTAL
        this.rvInteresesHO.setAdapter(InteresAdapter(viewModel.interesArray))
        this.rvInteresesHO.setLayoutManager(layout)
    }
}