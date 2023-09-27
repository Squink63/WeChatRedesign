package com.padcmyanmar.abbc.wechatredesign.data.models

import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.network.auth.AuthManager

interface AuthenticationModel {

    var mAuthManager: AuthManager

    fun login(email:String, password: String, phoneNumber: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun register(email: String, phoneNumber: String, password: String, userName: String, profilePicture: String, birthDate: String, gender: String,
                 onSuccess: (user: UserVO) -> Unit, onFailure: (String) -> Unit)

    fun getUserId(): String
}