package com.padcmyanmar.abbc.wechatredesign.data.models

import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.network.auth.AuthManager
import com.padcmyanmar.abbc.wechatredesign.network.auth.FirebaseAuthManager

object AuthenticationModelImpl: AuthenticationModel {
    override var mAuthManager: AuthManager = FirebaseAuthManager


    override fun login(
        email: String,
        password: String,
        phoneNumber: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.login(email, password, phoneNumber, onSuccess, onFailure)
    }

    override fun register(
        email: String,
        phoneNumber: String,
        password: String,
        userName: String,
        profilePicture: String,
        birthDate: String,
        gender: String,
        onSuccess: (user: UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.register(email, phoneNumber, password, userName, profilePicture, birthDate, gender, onSuccess, onFailure)
    }

    override fun getUserId(): String {
      return mAuthManager.getUserId()
    }
}