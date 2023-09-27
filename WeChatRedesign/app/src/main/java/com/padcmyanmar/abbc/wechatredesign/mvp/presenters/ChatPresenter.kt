package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import com.padcmyanmar.abbc.wechatredesign.delegates.GroupItemDelegate
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ChatView

interface ChatPresenter: BasePresenter<ChatView>, ChatItemDelegate, GroupItemDelegate {

    fun getContacts(scannerId:String)

    fun getUserId() : String

    fun getChatHistoryUserId(
        senderId: String
    )

    fun getGroupMessages(groupId: Long, onSuccess: (Int) -> Unit)
}