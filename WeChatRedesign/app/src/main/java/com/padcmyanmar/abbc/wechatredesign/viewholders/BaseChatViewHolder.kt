package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO

abstract class BaseChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bindData(message: MessageVO)
}