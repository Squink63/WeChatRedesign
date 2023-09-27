package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import com.padcmyanmar.abbc.wechatredesign.delegates.FirstLetterItemDelegate
import com.padcmyanmar.abbc.wechatredesign.mvp.views.AddGroupView

interface AddGroupPresenter: BasePresenter<AddGroupView>, FirstLetterItemDelegate, ChatItemDelegate {

    fun getContacts(scannerId:String)
    fun getUserId() :String
    fun onTapCreateButton(timeStamp: Long, groupName: String, userList:List<String>, imageUrl:String)
    fun onTapProfileImage()
    fun uploadGroupCoverPhoto(timeStamp: Long, bitmap: Bitmap)
}