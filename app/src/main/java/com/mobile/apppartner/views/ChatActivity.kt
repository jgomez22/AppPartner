package com.mobile.apppartner.views

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.mobile.apppartner.models.ChatPojo
import com.mobile.apppartner.R
import com.mobile.apppartner.viewmodels.ChatViewModel
import com.mobile.apppartner.databinding.ActivityChatBinding
import com.mobile.apppartner.interfaces.Observer
import com.mobile.apppartner.utils.Utils
import com.mobile.apppartner.views.adapter.ChatAdapter

class ChatActivity : AppCompatActivity(), Observer<ArrayList<ChatPojo>> {

    private var mBinding: ActivityChatBinding? = null
    private var mViewModel: ChatViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
      //  mViewModel = ChatViewModel(getIntent().getStringExtra(EXTRA_ROOM_NAME))
       mBinding!!.setVModel(mViewModel)
        mBinding!!.setActivity(this)
        mBinding!!.recyclerView.setLayoutManager(LinearLayoutManager(this))

//        mViewModel!!.addObserver(this)
        mViewModel!!.setListener()
    }

    fun sendMessage() {
        mViewModel!!.sendMessageToFirebase(mBinding!!.edittextChatMessage.getText().toString())
        mBinding!!.edittextChatMessage.getText().clear()
    }

    override fun onObserve(event: Int, eventMessage: ArrayList<ChatPojo>) {
        val chatAdapter = ChatAdapter(this, eventMessage)
        mBinding!!.recyclerView.setAdapter(chatAdapter)
        mBinding!!.recyclerView.scrollToPosition(eventMessage.size - 1)
    }

    override protected fun onDestroy() {
        super.onDestroy()
  //      mViewModel!!.removeObserver(this)
        mViewModel!!.onDestory()
    }
}
