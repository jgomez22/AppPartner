package com.mobile.apppartner.Views

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.RegisterViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        this.btnRegistrar.setOnClickListener {
            val email = txtCorreoRe.text.toString()
            val password = txtPasswordRe.text.toString()
            viewModel.createAccount(email,password,this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                Toast.makeText(this, "Felicitaciones ${it.email}, ya te encuentras registrado", Toast.LENGTH_LONG).show()
            },{error ->
                Toast.makeText(this,error.message, Toast.LENGTH_LONG).show()
            })
        }
    }
}
