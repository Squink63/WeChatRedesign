package com.padcmyanmar.abbc.wechatredesign.mvp.views

import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO

interface CreateMomentView: BaseView {

    fun navigateToBackScreen()
    fun openGallery()
    fun showUserInformation(userList: List<UserVO>)
}