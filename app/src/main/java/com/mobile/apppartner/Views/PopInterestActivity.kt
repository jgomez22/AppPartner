package com.mobile.apppartner.Views

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import com.mobile.apppartner.R
import kotlinx.android.synthetic.main.activity_pop_interest.*
import android.content.Intent
import android.graphics.Color
import android.widget.CheckBox
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioGroup


class PopInterestActivity : AppCompatActivity() {

    var intereses:MutableList<Boolean>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pop_interest)
        supportActionBar?.hide()
        intereses = intent.getBooleanArrayExtra("intereses").toMutableList()
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val height = dm.heightPixels
        val width = dm.widthPixels
        window.setLayout((width*.8).toInt(), (height*.5).toInt())
        setCheckBox()
    }

    fun setCheckBox(){

        val array = resources.getStringArray(R.array.interets_array)

        for (i in 0..array.size-1) {
            val ch = CheckBox(this)
            ch.text = array[i].toString()
            ch.textSize= 18F
            ch.setTextColor(Color.BLACK);

            ch.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    if(isChecked) intereses!![i]=true else intereses!![i]=false
                }
            })
            ch.isChecked = intereses!![i]
            lnCheckBox.addView(ch)
        }
    }

    override fun finish() {
        setResult(200, intent)
        intent.putExtra("intereses",intereses!!.toBooleanArray())
        super.finish()
    }

    override fun onBackPressed() {
        finish()
    }

}
