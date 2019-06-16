package com.example.appprueba

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.apppartner.R
import com.mobile.apppartner.databinding.FragmentChatBinding
import com.mobile.apppartner.ui.chat.UserMatchListViewModel
import com.mobile.apppartner.ui.chat.ViewModelChatFactory

class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    private lateinit var viewModel: UserMatchListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        binding.userMatchList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        viewModel = ViewModelProviders.of(this, ViewModelChatFactory()).get(UserMatchListViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }
}