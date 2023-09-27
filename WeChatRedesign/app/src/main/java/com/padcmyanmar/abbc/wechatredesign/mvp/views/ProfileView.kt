package com.padcmyanmar.abbc.wechatredesign.mvp.views

import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO

interface ProfileView: BaseView {

    fun showUserInfo(userList: List<UserVO>)
    fun showEditProfileDialog()
    fun openGallery()
    fun showQRCode()

    fun showMoments(momentList: List<MomentVO>)
    fun getMomentIsBookmarked(id: String, bookmarked: Boolean)
}