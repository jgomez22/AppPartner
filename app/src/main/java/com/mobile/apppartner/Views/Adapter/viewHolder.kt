package com.mobile.apppartner.Views.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.mobile.apppartner.Models.Interes
import com.mobile.apppartner.R
import kotlinx.android.synthetic.main.card_item_interest.view.*

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