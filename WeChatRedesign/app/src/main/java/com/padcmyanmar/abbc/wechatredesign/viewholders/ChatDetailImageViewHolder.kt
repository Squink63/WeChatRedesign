package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_holder_chat_detail_image.view.*

class ChatDetailImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindNewData(imageUrl:String) {

        Glide.with(itemView.context)
            .load(imageUrl)
            .into(itemView.ivSendImage)
    }

}