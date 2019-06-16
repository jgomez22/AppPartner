package com.mobile.apppartner.ui.message

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.mobile.apppartner.R
import com.mobile.apppartner.databinding.ActivityMessageListBinding

class MessageListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMessageListBinding
    private lateinit var viewModel: MessageListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_list)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_message_list)
        binding.messageList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(MessageListViewModel::class.java)
        binding.viewModel = viewModel
    }
}
