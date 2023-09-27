package com.padcmyanmar.abbc.wechatredesign.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.abbc.wechatredesign.viewholders.BaseChatViewHolder
import com.padcmyanmar.abbc.wechatredesign.viewholders.SenderViewHolder
import com.padcmyanmar.abbc.wechatredesign.viewholders.ReceiverViewHolder

class ChatMessageAdapter: RecyclerView.Adapter<BaseChatViewHolder>() {

    val VIEW_TYPE_SENDER = 1
    val VIEW_TYPE_RECEIVER = 2

    private var mMessageList: List<MessageVO> = listOf()
    private var mUserId: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseChatViewHolder {

        when(viewType) {
            VIEW_TYPE_SENDER -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_sender, parent, false)
                return SenderViewHolder(itemView)
            }

            VIEW_TYPE_RECEIVER -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_receiver, parent, false)
                return ReceiverViewHolder(itemView)
            }
            else -> {
                val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_receiver, parent, false)
                return ReceiverViewHolder(itemView)
            }
        }

    }

    override fun onBindViewHolder(holder: BaseChatViewHolder, position: Int) {

        when (holder.javaClass) {
            SenderViewHolder::class.java -> (holder as SenderViewHolder).bindData(mMessageList[position])
            ReceiverViewHolder::class.java -> (holder as ReceiverViewHolder).bindData(mMessageList[position])
            else-> throw java.lang.IllegalArgumentException("Invalid")
        }
    }

    override fun getItemCount(): Int {
        return mMessageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = mMessageList[position]
        return if (mUserId == currentMessage.userId) {
            VIEW_TYPE_SENDER
        } else {
            VIEW_TYPE_RECEIVER
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(userId: String, messageList: List<MessageVO>) {
        mUserId = userId
        mMessageList = messageList
        notifyDataSetChanged()
    }
}