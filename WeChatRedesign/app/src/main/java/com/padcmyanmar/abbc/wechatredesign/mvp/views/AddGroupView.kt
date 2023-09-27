package com.padcmyanmar.abbc.wechatredesign.mvp.views

import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO

interface AddGroupView: BaseView {

    fun navigateToChatDetailScreen(userId: String)
    fun showContacts(userList: List<UserVO>)

    fun addUserToGroup(userId: String, isChecked: Boolean)
    fun openGallery()
    fun getGroupCoverImageUrl(imageUrl: String)
}