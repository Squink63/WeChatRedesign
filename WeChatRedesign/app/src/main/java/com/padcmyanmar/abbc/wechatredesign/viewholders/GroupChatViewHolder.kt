package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.delegates.GroupItemDelegate
import kotlinx.android.synthetic.main.view_holder_chat_list.view.*

class GroupChatViewHolder(itemView: View, private val delegate: GroupItemDelegate) : RecyclerView.ViewHolder(itemView) {

    private var mGroupId:Long? = null

    init {
        itemView.setOnClickListener {
            mGroupId?.let { id ->
                delegate.onTapGroupItem(id)
            }
        }
    }

    fun bindData(group: GroupVO) {
        mGroupId = group.id
        itemView.tvProfileName.text = group.name

        Glide.with(itemView.context)
            .load(group.imageUrl)
            .placeholder(R.drawable.imag1)
            .into(itemView.ivChatListPorfile)

    }
}