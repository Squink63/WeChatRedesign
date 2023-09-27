package com.padcmyanmar.abbc.wechatredesign.mvp.views

import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO

interface ChatView: BaseView {

    fun navigateToChatDetailScreen(userId: String)
    fun showContacts(contactList: List<UserVO>)
    fun showUserId(userIdList: List<String>)
    fun getUsers(userList: List<UserVO>)
    fun getGroups(groupList: List<GroupVO>)
    fun navigateToGroupChatDetailScreen(groupId: Long)
}