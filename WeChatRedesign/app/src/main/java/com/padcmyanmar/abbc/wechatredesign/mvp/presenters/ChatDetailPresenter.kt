package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ChatDetailView

interface ChatDetailPresenter: BasePresenter<ChatDetailView> {

    fun onTapGetImageButton()

    fun getUserId(): String
    fun sendMessage(senderId: String, receiverId: String, timeStamp: Long, message: MessageVO)

    fun getMessages(
        senderId: String,
        receiverId: String
    )

    fun uploadAndSendImage(bitmap: Bitmap)

    fun sendGroupMessage(groupId: Long, timeStamp:Long, message:MessageVO)

    fun getGroupMessages(
        groupId:Long
    )
}