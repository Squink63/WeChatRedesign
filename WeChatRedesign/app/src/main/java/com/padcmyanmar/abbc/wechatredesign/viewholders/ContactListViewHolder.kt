package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.adapters.ContactAdapter
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import kotlinx.android.synthetic.main.view_holder_contact_list.view.*

class ContactListViewHolder(itemView: View, private val delegate: ChatItemDelegate) : RecyclerView.ViewHolder(itemView) {

    private lateinit var mAdapter: ContactAdapter

    init {
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mAdapter = ContactAdapter(delegate)
        itemView.rvContact.adapter = mAdapter
        itemView.rvContact.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
    }

    fun bindData(firstLetter: Char, contactList: List<UserVO>, isGroup: Boolean) {

        itemView.tvContactFirstLetter.text = firstLetter.toString()

        val userList = arrayListOf<UserVO>()
        for (user in contactList) {
            if (firstLetter == user.userName[0]) {
                userList.add(user)
            }
        }
        mAdapter.setNewData(userList, isGroup)
    }
}