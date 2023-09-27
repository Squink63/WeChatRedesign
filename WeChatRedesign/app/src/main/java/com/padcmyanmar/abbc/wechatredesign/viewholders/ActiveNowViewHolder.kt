package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import kotlinx.android.synthetic.main.view_holder_active_now.view.*

class ActiveNowViewHolder(itemView: View, private val delegate: ChatItemDelegate) : RecyclerView.ViewHolder(itemView) {

    private var mUserId:String? = null
    init {
        itemView.setOnClickListener {
            mUserId?.let { id->
                delegate.onTapChatItem(id)
            }
        }
    }

    fun bindData(user: UserVO) {

        mUserId = user.userId
        Glide.with(itemView.context)
            .load(user.profilePicture)
            .into(itemView.ivActiveProfile)

        itemView.tvActiveNowUser.text = user.userName
    }
}