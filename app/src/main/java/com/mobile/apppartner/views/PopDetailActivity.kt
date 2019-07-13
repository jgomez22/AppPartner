package com.mobile.apppartner.views

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.mobile.apppartner.R
import com.mobile.apppartner.models.UserDatabase
import kotlinx.android.synthetic.main.activity_pop_detail.*

class PopDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_detail)
        supportActionBar?.hide()
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels
        val width = dm.widthPixels
        window.setLayout((width*.8).toInt(), (height*.5).toInt())
        var user:UserDatabase = UserDatabase()

        user.fullname = intent.getStringExtra("nombre")
        this.txtNam.setText("Nombre: ${user.fullname}")

        user.career = intent.getStringExtra("carrera")
        this.txtCar.setText("Carrera: ${user.career}")

        user.age= intent.getStringExtra("edad")
        this.txtAg.setText("Edad: ${user.age}")

        user.campus = intent.getStringExtra("campus")
        this.txtCamp.setText("Campus: ${user.campus}")

        user.telephone = intent.getStringExtra("telefono")
        this.txtTel.setText("Celular: ${user.telephone}")

        user.descripcion = intent.getStringExtra("descripcion")
        this.txtDesc.setText("Descripción ${user.descripcion}")

    }
}
