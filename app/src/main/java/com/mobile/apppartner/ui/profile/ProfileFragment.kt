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
import com.github.mikephil.charting.data.*
import com.mobile.apppartner.Components.CircleTransformation
import com.mobile.apppartner.models.UserDatabase
import com.mobile.apppartner.R
import com.mobile.apppartner.ui.profile.ProfileViewModel
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    lateinit var viewModel: ProfileViewModel
    var us: UserDatabase? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        viewModel.getDataReport()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewModel.days = it
                buildChart()
            }, {
                print(it.message)
            }
            ).isDisposed

        viewModel.getInfoCurrentUser(this)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                us = it
                setValues()
            }, {
                print(it.toString())
            })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.btnSalir.setOnClickListener {
            viewModel.signOut(activity!!)
        }
    }

    fun setValues() {
        this.txtNombrePR.setText(us?.fullname.toString())
        this.txtDescripcionPR.setText(us?.descripcion.toString())
        Picasso.get().load(us?.url_img.toString()).transform(CircleTransformation()).into(this.imgPerfilPR)
    }

    fun buildChart() {
        var barChart: BarChart?
        barChart = this.chartBarChat
        barChart!!.setDrawBarShadow(false)
        barChart.setDrawValueAboveBar(true)
        barChart.setMaxVisibleValueCount(20)
        barChart.setPinchZoom(true)
        barChart.setDrawGridBackground(true)

        var barEntries: MutableList<BarEntry> = ArrayList()
        barEntries.add(BarEntry(1f, viewModel.days!!.count6!!.toFloat()))
        barEntries.add(BarEntry(2f, viewModel.days!!.count5!!.toFloat()))
        barEntries.add(BarEntry(3f, viewModel.days!!.count4!!.toFloat()))
        barEntries.add(BarEntry(4f, viewModel.days!!.count3!!.toFloat()))
        barEntries.add(BarEntry(5f, viewModel.days!!.count2!!.toFloat()))
        barEntries.add(BarEntry(6f, viewModel.days!!.count1!!.toFloat()))
        barEntries.add(BarEntry(7f, viewModel.days!!.count0!!.toFloat()))
        var barDataList: MutableList<BarDataSet> = ArrayList()
        var firstDay = BarDataSet(barEntries, "Últimos 7 días")
        firstDay.color = Color.RED
        barDataList.add(firstDay)

        var barData = BarData(barDataList.toList())
        barData.setBarWidth(0.4f)
        barChart.data = barData

    }

}
