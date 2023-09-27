package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import com.padcmyanmar.abbc.wechatredesign.mvp.views.LoginView

interface LoginPresenter: BasePresenter<LoginView> {

    fun onTapBackButton()
    fun onTapLoginButton(email: String, password: String,phoneNumber: String)
}