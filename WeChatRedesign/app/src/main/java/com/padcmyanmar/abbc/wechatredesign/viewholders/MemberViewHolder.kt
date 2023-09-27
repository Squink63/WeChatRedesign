package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import kotlinx.android.synthetic.main.view_holder_member.view.*

class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(user: UserVO) {

        itemView.tvProfileName.text = user.userName

        Glide.with(itemView.context)
            .load(user.profilePicture)
            .into(itemView.ivContactProfileImage)
    }
}