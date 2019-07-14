package com.example.appprueba

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.*
import com.mobile.apppartner.models.Interes
import com.mobile.apppartner.R
import com.mobile.apppartner.viewmodels.fragments.HomeViewModel
import com.mobile.apppartner.views.PopDetailActivity
import com.mobile.apppartner.views.adapter.InteresAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var ref: DatabaseReference
    var asd: MutableList<Interes> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
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
                        viewModel.userArray = it
                        viewModel.setUserIntoCardProfile()
                    }, {
                        Toast.makeText(this.context, it.message, Toast.LENGTH_LONG).show()
                    })
            }, {
                print(it.toString())
            })//new commit

        viewModel.getInteres()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewModel.interesArray = it
                viewModel.prueba(this)
                //getInteres()
            }, {
                print(it.message)
            }
            ).isDisposed

        btnNext.setOnClickListener {
            if (viewModel.userArray.size != 0) {
                viewModel.setUserIntoCardProfile()
            }
        }

        btnMatch.setOnClickListener {
            viewModel.sendMatch(uid.text.toString())
            Toast.makeText(this.context,"Se envio su solicitud",Toast.LENGTH_LONG).show()
        }

        btnDetail.setOnClickListener{
            val i = Intent(this.context,PopDetailActivity::class.java)
            i.putExtra("nombre",viewModel.user!!.fullname)
            i.putExtra("carrera",viewModel.user!!.career)
            i.putExtra("edad",viewModel.user!!.age)
            i.putExtra("campus",viewModel.user!!.campus)
            i.putExtra("telefono",viewModel.user!!.telephone)
            i.putExtra("descripcion",viewModel.user!!.descripcion)
            this.startActivityForResult(i,200)
            /*
                var uid:String? = "",
                var email:String= "",
                var fullname:String= "",
                var url_img:String= "",
                var interest:MutableList<Int> = mutableListOf(),
                var career:String= "",
                var campus:String= "",
                var telephone:String= "",
                var age:String= "",
                var descripcion:String=""
             */
        }
    }

    fun getInteres() {
        this.rvInteresesHO.setHasFixedSize(true)
        val layout = LinearLayoutManager(this.context)
        layout.orientation = LinearLayoutManager.HORIZONTAL
        this.rvInteresesHO.setAdapter(InteresAdapter(viewModel.interesArray))
        this.rvInteresesHO.setLayoutManager(layout)
    }
}