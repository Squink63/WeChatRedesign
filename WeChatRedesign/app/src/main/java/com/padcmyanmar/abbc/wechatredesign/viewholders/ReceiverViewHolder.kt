package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO
import kotlinx.android.synthetic.main.view_holder_receiver.view.*
import java.text.SimpleDateFormat
import java.util.*

class ReceiverViewHolder(itemView: View) : BaseChatViewHolder(itemView) {

    override fun bindData(message: MessageVO) {

        Glide.with(itemView.context)
            .load(message.userProfileImage)
            .into(itemView.ivReceiverImage)

        if (message.message.isEmpty()){
            itemView.llReceiveChat.visibility = View.GONE
            itemView.llImageReceive.visibility = View.VISIBLE

            Glide.with(itemView.context)
                .load(message.file)
                .into(itemView.ivReceiverImageMessage)

            itemView.tvReceiverMessageTimeImage.text = getCurrentHourAndMinutes(message.timeStamp)

        } else if (message.message.isNotEmpty() && message.file.isEmpty()) {
            itemView.llReceiveChat.visibility = View.VISIBLE
            itemView.llImageReceive.visibility = View.GONE

            itemView.tvReceiverMessage.text = message.message
            itemView.tvReceiverMessageTime.text = getCurrentHourAndMinutes(message.timeStamp)

        } else {
            itemView.llReceiveChat.visibility = View.VISIBLE
            itemView.llImageReceive.visibility = View.VISIBLE

            itemView.tvReceiverMessage.text = message.message
            itemView.tvReceiverMessageTime.text = getCurrentHourAndMinutes(message.timeStamp)

            Glide.with(itemView.context)
                .load(message.file)
                .into(itemView.ivReceiverImageMessage)

            itemView.tvReceiverMessageTimeImage.text = getCurrentHourAndMinutes(message.timeStamp)
        }
    }

    private fun getCurrentHourAndMinutes(currentTimeMillis:Long) :String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis

        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return timeFormat.format(calendar.time)
    }

}