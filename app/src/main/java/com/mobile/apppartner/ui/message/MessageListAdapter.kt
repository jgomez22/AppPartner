package com.mobile.apppartner.ui.message

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.apppartner.R
import com.mobile.apppartner.databinding.ItemMessageBinding
import com.mobile.apppartner.models.Message

class MessageListAdapter : RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {

    private lateinit var messageList: List<Message?>

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messageList[position]!!)
    }

    override fun getItemCount(): Int {
        return if (::messageList.isInitialized) messageList.size else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMessageBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_message, parent, false)
        return ViewHolder(binding)
    }

    fun updateMessageList(messageList: List<Message?>) {
        this.messageList = messageList
        notifyDataSetChanged()
    }

    fun sendMessage(message: Message) {
        this.messageList = messageList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = MessageViewModel()

        fun bind(message: Message) {
            viewModel.bind(message)
            binding.viewModel = viewModel
            if (message.isMine){
                binding.txtMyMessage.visibility = View.VISIBLE
                binding.txtMyMessageTime.visibility = View.VISIBLE
                binding.txtOtherMessage.visibility = View.GONE
                binding.txtOtherMessageTime.visibility = View.GONE
            }else{
                binding.txtMyMessage.visibility = View.GONE
                binding.txtMyMessageTime.visibility = View.GONE
                binding.txtOtherMessage.visibility = View.VISIBLE
                binding.txtOtherMessageTime.visibility = View.VISIBLE
            }
        }
    }

}