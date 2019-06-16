package com.mobile.apppartner.utils

import android.app.PendingIntent.getActivity
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.mobile.apppartner.ui.message.MessageListActivity
import kotlinx.android.synthetic.main.activity_register.view.*
import net.gahfy.mvvmposts.utils.extension.getParentActivity

@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View,  visibility: MutableLiveData<Int>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value?:View.VISIBLE})
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView,  text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value ->
            view.text = value?:""})
    }
}

@BindingAdapter("onUserMatchOnclick")
fun onUserMatchOnclick(relativeLayout: RelativeLayout, text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = relativeLayout.getParentActivity()
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value ->
            relativeLayout.setOnClickListener {
                val intent = Intent(relativeLayout.context,MessageListActivity::class.java)
                intent.putExtra("uidDestination" , ((relativeLayout.getChildAt(1) as ViewGroup).getChildAt(0) as TextView).text)
                relativeLayout.context.startActivity(intent)
            }})
    }
}