package com.padcmyanmar.abbc.wechatredesign.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import com.padcmyanmar.abbc.wechatredesign.viewholders.ContactViewHolder

class ContactAdapter(
    private val delegate: ChatItemDelegate
): RecyclerView.Adapter<ContactViewHolder>() {

    private var mContactList: ArrayList<UserVO> = arrayListOf()
    private var mIsGroup = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_contact, parent, false)
        return ContactViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return mContactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bindData(mContactList[position], mIsGroup)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(userList: ArrayList<UserVO>, isGroup: Boolean) {

        mContactList = userList
        mIsGroup = isGroup
        notifyDataSetChanged()
    }
}