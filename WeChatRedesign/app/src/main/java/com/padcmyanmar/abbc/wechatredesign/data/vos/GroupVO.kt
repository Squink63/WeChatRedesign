package com.padcmyanmar.abbc.wechatredesign.data.vos

data class GroupVO (
    var id: Long = 0L,
    var name: String = "",
    var userIdList: List<String> = listOf(),
    var messageList: List<MessageVO> = listOf(),
    var imageUrl:String = ""
        )