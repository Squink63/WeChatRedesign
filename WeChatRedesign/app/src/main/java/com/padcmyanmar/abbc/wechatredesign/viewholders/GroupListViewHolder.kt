package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.delegates.GroupItemDelegate
import kotlinx.android.synthetic.main.view_holder_group_list.view.*

class GroupListViewHolder(itemView: View, private val delegate: GroupItemDelegate) : RecyclerView.ViewHolder(itemView) {

    fun bindData(group: GroupVO) {

        itemView.tvGroupName.text = group.name
        itemView.setOnClickListener {
            delegate.onTapGroupItem(group.id)
        }

        Glide.with(itemView.context)
            .load(group.imageUrl)
            .placeholder(R.drawable.imag1)
            .into(itemView.ivGroup)
    }
}