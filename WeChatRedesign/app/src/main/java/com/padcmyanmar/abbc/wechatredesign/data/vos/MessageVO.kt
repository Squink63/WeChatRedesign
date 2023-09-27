package com.padcmyanmar.abbc.wechatredesign.data.vos

data class MessageVO (
    var userId: String = "",
    var userName: String = "",
    var userProfileImage: String = "",
    var timeStamp: Long = 0L,
    var file: String = "",
    var message: String = "",
    var groupName: String = ""
        )