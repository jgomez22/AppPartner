package com.mobile.apppartner.views.adapter

import android.content.Context
import android.support.v4.app.Fragment
import com.mobile.apppartner.R
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mobile.apppartner.models.Interes

public class InteresAdapter:RecyclerView.Adapter<viewHolder> {

    var list:MutableList<Interes>

    constructor(list:MutableList<Interes>){
        this.list=list
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): viewHolder {
        val vista:View = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_item_interest, viewGroup, false)
        return viewHolder(vista,list)
    }

    override fun getItemCount(): Int {return list.size}

    override fun onBindViewHolder(viewHolder: viewHolder, p1: Int) {
        viewHolder.nombre.setText(list.get(p1).name.toString())
        when(list.get(p1).id){

            "0"-> viewHolder.imagen.setImageResource(R.drawable.ic_study)
            "1"-> viewHolder.imagen.setImageResource(R.drawable.ic_concert)
            "2"-> viewHolder.imagen.setImageResource(R.drawable.ic_cinema)
            "3"-> viewHolder.imagen.setImageResource(R.drawable.ic_love)
            "4"-> viewHolder.imagen.setImageResource(R.drawable.ic_sport)
            "5"-> viewHolder.imagen.setImageResource(R.drawable.ic_travel)
            "6"-> viewHolder.imagen.setImageResource(R.drawable.ic_other)
        }
        viewHolder.imagen.setOnClickListener {
            
        }
    }



}