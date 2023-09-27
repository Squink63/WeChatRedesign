package com.padcmyanmar.abbc.wechatredesign.delegates

interface ChatItemDelegate {

    fun onTapChatItem(userId:String)
    fun onTapCheckbox(userId: String, isChecked: Boolean)
}