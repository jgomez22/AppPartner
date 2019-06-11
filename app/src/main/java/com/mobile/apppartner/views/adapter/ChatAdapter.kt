package com.mobile.apppartner.views.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mobile.apppartner.BR
import com.mobile.apppartner.databinding.RowChatAdapterBinding
import com.mobile.apppartner.models.ChatPojo
import com.mobile.apppartner.R

class ChatAdapter(private val mContext: Context, private val chatList: ArrayList<ChatPojo>) : RecyclerView.Adapter<ChatAdapter.BindingHolder>() {

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.row_chat_adapter, parent, false)
        val binding = DataBindingUtil.bind<RowChatAdapterBinding>(view)
        return BindingHolder(binding!!)
    }

    override fun onBindViewHolder(holder: BindingHolder, p1: Int) {
        holder.binding!!.setVariable(BR.chatMessage, chatList.get(p1))
        holder.binding!!.executePendingBindings()

    }

    inner class BindingHolder(binding: RowChatAdapterBinding) : RecyclerView.ViewHolder(binding.getRoot()) {

        var binding: RowChatAdapterBinding? = null

        init {
            this.binding = binding
        }
    }
}