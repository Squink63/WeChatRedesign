package com.padcmyanmar.abbc.wechatredesign.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.delegates.GroupItemDelegate
import com.padcmyanmar.abbc.wechatredesign.viewholders.ChatListViewHolder
import com.padcmyanmar.abbc.wechatredesign.viewholders.GroupListViewHolder
import com.padcmyanmar.abbc.wechatredesign.viewholders.MomentViewHolder

class GroupListAdapter(
    private val delegate: GroupItemDelegate
): RecyclerView.Adapter<GroupListViewHolder>() {
    private var mGroupList: ArrayList<GroupVO> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_group_list, parent, false)
        return GroupListViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: GroupListViewHolder, position: Int) {
        holder.bindData(mGroupList[position])
    }

    override fun getItemCount(): Int {
        return mGroupList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(groupList: ArrayList<GroupVO>) {
        mGroupList = groupList
        notifyDataSetChanged()
    }
}