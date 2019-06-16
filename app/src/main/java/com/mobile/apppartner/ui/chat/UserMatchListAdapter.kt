package com.mobile.apppartner.ui.chat

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.mobile.apppartner.R
import com.mobile.apppartner.models.UserMatch
import com.mobile.apppartner.databinding.ItemUserMatchBinding

class UserMatchListAdapter : RecyclerView.Adapter<UserMatchListAdapter.ViewHolder>() {

    private lateinit var userMatchList: List<UserMatch?>

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userMatchList[position]!!)
    }

    override fun getItemCount(): Int {
        return if (::userMatchList.isInitialized) userMatchList.size else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemUserMatchBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_user_match, parent, false)
        return ViewHolder(binding)
    }

    fun updateUserMatch(userMatchList: List<UserMatch?>) {
        this.userMatchList = userMatchList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemUserMatchBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = UserMatchViewModel()

        fun bind(user: UserMatch) {
            viewModel.bind(user)
            binding.viewModel = viewModel
        }
    }

}