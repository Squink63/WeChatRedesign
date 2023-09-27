package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import kotlinx.android.synthetic.main.view_holder_chat_list.view.*

class ChatListViewHolder(itemView: View, private val delegate: ChatItemDelegate) : RecyclerView.ViewHolder(itemView) {

    private var mUserId:String? = null
    init {
        itemView.setOnClickListener {
            mUserId?.let {id ->
                delegate.onTapChatItem(id)
            }
        }
    }

    fun bindData(user: UserVO) {

        mUserId = user.userId
        itemView.tvProfileName.text = user.userName
        Glide.with(itemView.context)
            .load(user.profilePicture)
            .into(itemView.ivChatListPorfile)
    }

}