package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import kotlinx.android.synthetic.main.view_holder_contact.view.*

class ContactViewHolder(itemView: View, private val delegate: ChatItemDelegate) : RecyclerView.ViewHolder(itemView) {

    private var mUser: UserVO? = null

    init {
        itemView.setOnClickListener {
            delegate.onTapChatItem(mUser?.userId ?: "")
        }
    }

    fun bindData(user: UserVO, isGroup: Boolean) {
        mUser = user
        itemView.tvContactProfileName.text = user.userName

        Glide.with(itemView.context)
            .load(user.profilePicture)
            .into(itemView.ivContactProfile)

        if (isGroup) {
            itemView.cbAddGroup.visibility = View.VISIBLE

            itemView.cbAddGroup.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    delegate.onTapCheckbox(user.userId, true)
                } else{
                    delegate.onTapCheckbox(user.userId, false)
                }
            }
        } else {
            itemView.cbAddGroup.visibility = View.GONE
        }
    }

}