package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO
import kotlinx.android.synthetic.main.view_holder_sender.view.*
import java.text.SimpleDateFormat
import java.util.*

class SenderViewHolder(itemView: View) : BaseChatViewHolder(itemView) {

    override fun bindData(message: MessageVO) {
        if (message.message.isEmpty() && message.file.isNotEmpty()){
            itemView.llSenderMessage.visibility = View.GONE
            itemView.llImage.visibility = View.VISIBLE

            Glide.with(itemView.context)
                .load(message.file)
                .into(itemView.ivSenderImageMessage)

            itemView.tvSenderMessageTime.text = getCurrentHourAndMinutes(message.timeStamp)

        } else if (message.message.isNotEmpty() && message.file.isEmpty()) {
            itemView.llSenderMessage.visibility = View.VISIBLE
            itemView.llImage.visibility = View.GONE

            itemView.tvSenderMessage.text = message.message
            itemView.tvSenderMessageTimeText.text = getCurrentHourAndMinutes(message.timeStamp)

        } else {
            itemView.llSenderMessage.visibility = View.VISIBLE
            itemView.llImage.visibility = View.VISIBLE

            itemView.tvSenderMessage.text = message.message
            itemView.tvSenderMessageTimeText.text = getCurrentHourAndMinutes(message.timeStamp)

            Glide.with(itemView.context)
                .load(message.file)
                .into(itemView.ivSenderImageMessage)

            itemView.tvSenderMessageTime.text = getCurrentHourAndMinutes(message.timeStamp)
        }
    }

    private fun getCurrentHourAndMinutes(currentTimeMillis:Long) :String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis

        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return timeFormat.format(calendar.time)
    }
}