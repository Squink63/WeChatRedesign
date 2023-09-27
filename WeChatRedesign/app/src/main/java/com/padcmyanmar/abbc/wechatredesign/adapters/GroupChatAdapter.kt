package com.padcmyanmar.abbc.wechatredesign.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.delegates.GroupItemDelegate
import com.padcmyanmar.abbc.wechatredesign.viewholders.GroupChatViewHolder

class GroupChatAdapter(
    private val delegate: GroupItemDelegate
): RecyclerView.Adapter<GroupChatViewHolder>() {

    private var mGroupList:ArrayList<GroupVO> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_list, parent, false)
        return GroupChatViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mGroupList.size
    }

    override fun onBindViewHolder(holder: GroupChatViewHolder, position: Int) {
        holder.bindData(mGroupList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(group: GroupVO) {
        mGroupList.add(group)
        notifyDataSetChanged()
    }
}