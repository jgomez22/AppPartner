package com.mobile.apppartner.Views

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        this.btnActivarCuenta.setOnClickListener {
            viewModel.goToRegister(this)
        }

        this.btnLogin.setOnClickListener {
            val email = this.txtEmail.text.toString()
            val password = this.txtPassword.text.toString()
            viewModel.logInViewModel(email,password,this)
                .subscribe(
                    {it->
                        Toast.makeText(this,"cuenta logeada correctamente",Toast.LENGTH_LONG).show()
                    }
                    ,{error->
                        Toast.makeText(this,error.message,Toast.LENGTH_LONG).show()
                    })
        }
    }
}
