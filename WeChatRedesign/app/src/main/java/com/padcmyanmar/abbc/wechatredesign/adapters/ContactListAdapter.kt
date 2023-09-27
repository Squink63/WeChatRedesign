package com.padcmyanmar.abbc.wechatredesign.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import com.padcmyanmar.abbc.wechatredesign.viewholders.ChatListViewHolder
import com.padcmyanmar.abbc.wechatredesign.viewholders.ContactListViewHolder
import com.padcmyanmar.abbc.wechatredesign.viewholders.MomentViewHolder

class ContactListAdapter(
    private val delegate: ChatItemDelegate
): RecyclerView.Adapter<ContactListViewHolder>() {

    private var mLetterList: List<Char> = listOf()
    private var mUserList:List<UserVO> = listOf()
    private var mIsGroup = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_contact_list, parent, false)
        return ContactListViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {

        holder.bindData(mLetterList[position], mUserList, mIsGroup)
    }

    override fun getItemCount(): Int {
        return mLetterList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(letterList: List<Char>, contactList: List<UserVO>, isGroup: Boolean) {
        mLetterList = letterList
        mUserList = contactList
        mIsGroup = isGroup
        notifyDataSetChanged()
    }
}