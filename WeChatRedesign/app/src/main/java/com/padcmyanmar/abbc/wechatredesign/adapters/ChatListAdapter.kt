package com.padcmyanmar.abbc.wechatredesign.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import com.padcmyanmar.abbc.wechatredesign.viewholders.ChatListViewHolder
import com.padcmyanmar.abbc.wechatredesign.viewholders.MomentViewHolder
import kotlinx.android.synthetic.main.view_holder_chat_list.view.*

class ChatListAdapter(
    private val delegate: ChatItemDelegate
): RecyclerView.Adapter<ChatListViewHolder>() {

    private var mUserList: ArrayList<UserVO> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_list, parent, false)
        return ChatListViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: ChatListViewHolder, position: Int) {
        holder.bindData(mUserList[position])
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(chatUserList: ArrayList<UserVO>) {
        mUserList = chatUserList
        notifyDataSetChanged()
    }
}