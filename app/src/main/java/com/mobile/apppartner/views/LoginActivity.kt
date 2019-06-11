package com.mobile.apppartner.views

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mobile.apppartner.R
import com.mobile.apppartner.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        this.btnActivarCuenta.setOnClickListener {
            viewModel.signInOffice365(this).subscribe({
                viewModel.goToRegister(it.email.toString(),it.fullname.toString(),this)
            },{
                Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
            })
        }

        this.btnLogin.setOnClickListener {
            val email = this.txtCorreoLo.text.toString()
            val password = this.txtPasswordLo.text.toString()
            viewModel.logInViewModel(email,password,this)
                .subscribe(
                    {it->
                        Toast.makeText(this,"Bienvenido ${it.email}",Toast.LENGTH_LONG).show()
                        viewModel.goToMain(this)
                    }
                    ,{error->
                        Toast.makeText(this,error.message,Toast.LENGTH_LONG).show()
                    })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel.onActivityResult(requestCode,resultCode,data)
    }
}
