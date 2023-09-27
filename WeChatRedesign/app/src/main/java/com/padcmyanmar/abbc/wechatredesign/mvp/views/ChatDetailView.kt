package com.padcmyanmar.abbc.wechatredesign.mvp.views

import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO

interface ChatDetailView: BaseView {

    fun showUsers(userList: List<UserVO>)
    fun showMessages(messageList: List<MessageVO>)
    fun openGallery()
    fun getImageUrlForFile(file: String)

    fun showGroupMessages(messageList: List<MessageVO>)
    fun getGroups(groupList: List<GroupVO>)
}