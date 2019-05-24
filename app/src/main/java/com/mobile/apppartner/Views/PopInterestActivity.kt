package com.mobile.apppartner.Views

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.mobile.apppartner.R
import kotlinx.android.synthetic.main.activity_pop_interest.*
import android.content.Intent
import android.widget.CheckBox
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup


class PopInterestActivity : AppCompatActivity() {

    var values:MutableList<Boolean> = mutableListOf(false,false,false,false,false,false,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_interest)
        supportActionBar?.hide()

        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels
        val width = dm.widthPixels
        window.setLayout((width*.5).toInt(), (height*.5).toInt())

        setCheckBox()

        btnAddInt.setOnClickListener {
            val asd = values.toBooleanArray()

            intent.putExtra("value",asd)
            //intent.putExtra("value", values)
            setResult(200, intent)
            finish()
        }

    }

    fun setCheckBox(){

        val length = resources.getStringArray(R.array.interets_array).size

        for (i in 0..length-1) {
            val ch = CheckBox(this)
            ch.text = resources.getStringArray(R.array.sede_array)[i].toString()
            ch.textSize= 18F
            //ch.id = i
            ch.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

                    if(isChecked) values[i] = true else values[i] = false

                }
            })

            lnCheckBox.addView(ch)
        }
    }

}
