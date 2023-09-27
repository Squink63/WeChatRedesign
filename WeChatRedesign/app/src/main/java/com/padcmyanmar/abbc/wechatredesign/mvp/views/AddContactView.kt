package com.padcmyanmar.abbc.wechatredesign.mvp.views

import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO

interface AddContactView :BaseView {

    fun getUsers(userList: List<UserVO>, exporterUserId:String)
}