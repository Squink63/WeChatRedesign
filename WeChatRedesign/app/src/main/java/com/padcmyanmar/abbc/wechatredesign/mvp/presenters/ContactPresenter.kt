package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import com.padcmyanmar.abbc.wechatredesign.delegates.FirstLetterItemDelegate
import com.padcmyanmar.abbc.wechatredesign.delegates.GroupItemDelegate
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ContactView

interface ContactPresenter: BasePresenter<ContactView>, GroupItemDelegate, FirstLetterItemDelegate, ChatItemDelegate {

    fun onTapAddContactButton()
    fun onTapAddNewGroupButton()
    fun getContacts(scannerId: String)
    fun getUserId() :String
}