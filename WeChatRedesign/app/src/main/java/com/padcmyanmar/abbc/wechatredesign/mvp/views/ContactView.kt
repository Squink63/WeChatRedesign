package com.padcmyanmar.abbc.wechatredesign.mvp.views

import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO

interface ContactView: BaseView {

    fun navigateToAddContactScreen()
    fun navigateToAddGroupScreen()
    fun navigateToChatDetailScreen(userId: String)
    fun showContacts(contactList: List<UserVO>)
    fun addToGroup(userId: String)
    fun getGroupList(groupList: List<GroupVO>)
    fun navigateToChatDetailScreenFromGroup(groupId: String)
}