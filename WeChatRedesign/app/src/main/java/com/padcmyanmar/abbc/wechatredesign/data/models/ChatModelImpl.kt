package com.padcmyanmar.abbc.wechatredesign.data.models

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.abbc.wechatredesign.network.RealtimeDatabaseFirebaseApi
import com.padcmyanmar.abbc.wechatredesign.network.RealtimeDatabaseFirebaseImpl

object ChatModelImpl: ChatModel {
    override var mFirebaseApi: RealtimeDatabaseFirebaseApi = RealtimeDatabaseFirebaseImpl


    override fun getOtp(onSuccess: (code: String) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getOtp(onSuccess, onFailure)
    }

    override fun sendMessage(
        senderId: String,
        receiverId: String,
        timeStamp: Long,
        message: MessageVO
    ) {
        mFirebaseApi.sendMessage(senderId, receiverId, timeStamp, message)
    }

    override fun getMessages(
        senderId: String,
        receiverId: String,
        onSuccess: (groceries: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getMessages(senderId, receiverId, onSuccess, onFailure)
    }

    override fun uploadAndSendImage(
        bitmap: Bitmap,
        onSuccess: (file: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadAndSendImage(bitmap,onSuccess,onFailure)
    }

    override fun getChatHistoryUserId(
        senderId: String,
        onSuccess: (messageList: List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getChatHistoryUserId(senderId, onSuccess, onFailure)
    }

    override fun addGroup(
        timeStamp: Long,
        groupName: String,
        userList: List<String>,
        imageUrl: String
    ) {
        mFirebaseApi.addGroup(timeStamp, groupName, userList, imageUrl)
    }

    override fun uploadGroupCoverPhoto(
        timeStamp: Long,
        bitmap: Bitmap,
        onSuccess: (imageUrl: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadGroupCoverPhoto(timeStamp, bitmap, onSuccess, onFailure)
    }

    override fun getGroups(
        onSuccess: (groupIdList: List<GroupVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getGroups(onSuccess,onFailure)
    }

    override fun sendGroupMessage(groupId: Long, timeStamp:Long, message: MessageVO) {
        mFirebaseApi.sendGroupMessage(groupId,timeStamp, message)
    }

    override fun getGroupMessages(
        groupId: Long,
        onSuccess: (messageList: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getGroupMessages(groupId, onSuccess, onFailure)
    }
}