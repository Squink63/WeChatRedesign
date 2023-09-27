package com.padcmyanmar.abbc.wechatredesign.data.models

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.abbc.wechatredesign.network.RealtimeDatabaseFirebaseApi

interface ChatModel {

    var mFirebaseApi: RealtimeDatabaseFirebaseApi

    fun getOtp(
        onSuccess: (code: String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendMessage(senderId: String, receiverId: String,timeStamp:Long, message: MessageVO)

    fun getMessages(
        senderId: String,
        receiverId: String,
        onSuccess: (groceries: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun uploadAndSendImage(bitmap: Bitmap, onSuccess: (file: String) -> Unit, onFailure: (String) -> Unit)

    fun getChatHistoryUserId(
        senderId: String,
        onSuccess: (messageList: List<String>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addGroup(timeStamp: Long, groupName: String,userList:List<String>, imageUrl:String)

    fun uploadGroupCoverPhoto(
        timeStamp: Long,
        bitmap: Bitmap,
        onSuccess: (imageUrl: String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getGroups(
        onSuccess: (groupIdList: List<GroupVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendGroupMessage(groupId: Long,timeStamp:Long, message:MessageVO)

    fun getGroupMessages(
        groupId:Long,
        onSuccess: (messageList: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    )
}