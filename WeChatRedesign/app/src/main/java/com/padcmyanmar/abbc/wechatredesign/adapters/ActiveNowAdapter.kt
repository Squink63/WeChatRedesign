package com.padcmyanmar.abbc.wechatredesign.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import com.padcmyanmar.abbc.wechatredesign.viewholders.ActiveNowViewHolder
import com.padcmyanmar.abbc.wechatredesign.viewholders.MomentViewHolder

class ActiveNowAdapter(
    private val delegate: ChatItemDelegate
): RecyclerView.Adapter<ActiveNowViewHolder>() {

    private var mContactList: List<UserVO> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveNowViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_active_now, parent, false)
        return ActiveNowViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: ActiveNowViewHolder, position: Int) {
        holder.bindData(mContactList[position])
    }

    override fun getItemCount(): Int {
        return mContactList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(contactList: List<UserVO>) {
        mContactList = contactList
        notifyDataSetChanged()
    }
}