package com.padcmyanmar.abbc.wechatredesign.network.auth

import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO

interface AuthManager {

    fun login(email:String, password: String,phoneNumber: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun register(email: String, phoneNumber: String, password: String, userName: String, profilePicture: String, birthDate: String, gender: String,
    onSuccess: (user: UserVO) -> Unit, onFailure: (String) -> Unit)

    fun getUserId(): String


}