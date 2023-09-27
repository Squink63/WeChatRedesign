package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.views.AddContactView

interface AddContactPresenter: BasePresenter<AddContactView> {

    fun getUsers(exporterUserId:String)
    fun createContact(scannerId:String,exporterId:String,contact: UserVO)
    fun getScannerUserId(): String
}