package com.mobile.apppartner.views.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mobile.apppartner.models.Interes
import com.mobile.apppartner.R

class viewHolder:RecyclerView.ViewHolder{

    var nombre:TextView
    var imagen:ImageView
    var list:MutableList<Interes>

    constructor(itemView:View,list:MutableList<Interes>) : super(itemView) {
        this.nombre = this.itemView.findViewById(R.id.txtInteres)
        this.imagen = this.itemView.findViewById(R.id.ivInteresCV)
        this.list=list
    }
}