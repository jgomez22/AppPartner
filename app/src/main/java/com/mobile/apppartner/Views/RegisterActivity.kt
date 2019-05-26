package com.mobile.apppartner.Views

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.RegisterViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_register.*
import android.widget.AdapterView.OnItemSelectedListener
import android.R.attr.data





class RegisterActivity : AppCompatActivity() {

    lateinit var viewModel: RegisterViewModel
    var uri:Uri?=null
    lateinit var campus:String
    lateinit var career:String
    var intereses = mutableListOf<Boolean>(false,false,false,false,false,false,false)
    lateinit var valueOfInterest:MutableList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        val fullname = intent.getStringExtra("fullname").toString()
        val correo = intent.getStringExtra("correo").toString()
        txtNombreRe.setText(fullname)
        txtCorreoRe.setText(correo)

        this.btnRegistrar.setOnClickListener {
            val email = txtCorreoRe.text.toString()
            val password = txtPasswordRe.text.toString()
            getInterest()
            if(viewModel.validateUser(password,this)){
                viewModel.createAccount(email,password,this)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        viewModel.finishRegister(txtCorreoRe.text.toString(),txtNombreRe.text.toString(),campus,career)
                        Toast.makeText(this, "Felicitaciones ${it.email}, ya te encuentras registrado", Toast.LENGTH_LONG).show()
                        finish()
                    },{error ->
                        Toast.makeText(this,error.message, Toast.LENGTH_LONG).show()
                    })
            }
        }

        this.imgPerfilRe.setOnClickListener {
            viewModel.openImage(this)
        }

        this.btnAddInterest.setOnClickListener {
            val i = Intent(this,PopInterestActivity::class.java)
            i.putExtra("intereses",intereses.toBooleanArray())
            this.startActivityForResult(i,200)
        }

        spCampus.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                campus = parent?.getItemAtPosition(position).toString()
            }

        }

        spCareer.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                career = parent?.getItemAtPosition(position).toString()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data !=null){
            viewModel.onActivityResult(requestCode,resultCode,data,this)
        }
        if(requestCode==200){
            intereses = data!!.getBooleanArrayExtra("intereses").toMutableList()
        }
    }

    fun getInterest(){
        valueOfInterest = mutableListOf()
        for(i in 0 until intereses.size){
            if(intereses[i]==true) valueOfInterest.add(i)
        }
        viewModel.valueOfInterest = valueOfInterest
    }
}
