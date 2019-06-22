package com.example.appprueba

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.mobile.apppartner.Components.CircleTransformation
import com.mobile.apppartner.models.UserDatabase
import com.mobile.apppartner.R
import com.mobile.apppartner.models.Match
import com.mobile.apppartner.viewmodels.fragments.ProfileViewModel
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: Fragment() {

    lateinit var viewModel: ProfileViewModel
    var us:UserDatabase?=null
    var matchList : MutableList<Match>? = null
    lateinit var barChart : BarChart


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        viewModel.getInfoCurrentUser(this)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            us=it
            setValues()
        },{
            print(it.toString())
        })

        viewModel.getDataReport(this)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                matchList?.add(it)
                buildChart()
            },{
                print(it.toString())

            })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.btnSalir.setOnClickListener {
            viewModel.signOut(activity!!)
        }
    }
    fun setValues(){
        this.txtNombrePR.setText(us?.fullname.toString())
        this.txtDescripcionPR.setText(us?.descripcion.toString())
        Picasso.get().load(us?.url_img.toString()).transform(CircleTransformation()).into(this.imgPerfilPR)
    }

    fun buildChart(){

        matchList!!.forEach {
            x -> 
        }
        barChart = this.chartBarChat
        barChart!!.setDrawBarShadow(false)
        barChart!!.setDrawValueAboveBar(true)
        barChart!!.setMaxVisibleValueCount(20)
        barChart!!.setPinchZoom(true)
        barChart!!.setDrawGridBackground(true)

        var barEntries : MutableList<BarEntry> = ArrayList()

        barEntries.add(BarEntry(1f,4f))
        barEntries.add(BarEntry(2f,1f))
        barEntries.add(BarEntry(3f,1f))
        barEntries.add(BarEntry(4f,3f))
        barEntries.add(BarEntry(5f,3f))
        barEntries.add(BarEntry(6f,1f))
        barEntries.add(BarEntry(7f,0f))

        var barDataList : MutableList<BarDataSet> = ArrayList()
        var firstDay = BarDataSet(barEntries,"Lunes")
        firstDay.color=Color.RED
        barDataList.add(firstDay)

        var barData = BarData(barDataList.toList())
        barData.setBarWidth(0.4f)
        barChart.data=barData


    }

}