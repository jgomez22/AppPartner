package com.mobile.apppartner.Views

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.mobile.apppartner.R
import com.mobile.apppartner.ViewModels.RegisterViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    lateinit var viewModel: RegisterViewModel
    var uri:Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        txtNombreRe.setText(intent.getStringExtra("fullname"))
        txtCorreoRe.setText(intent.getStringExtra("correo"))

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

        this.imgPerfilRe.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/"
            startActivityForResult(intent,0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data !=null){
            uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,uri)
            val bitmapDrawable = BitmapDrawable(bitmap)
            imgPerfilRe.setBackgroundDrawable(bitmapDrawable)
        }
    }
}
