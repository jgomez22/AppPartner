package com.example.appprueba

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobile.apppartner.R
import com.mobile.apppartner.viewmodels.fragments.ChatViewModel
import com.mobile.apppartner.views.ChatActivity
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment: Fragment() {

    lateinit var viewModel: ChatViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*this.btnChat.setOnClickListener{
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("holu", "holu")
            startActivity(intent)
        }*/

    }
}