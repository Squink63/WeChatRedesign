package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import com.padcmyanmar.abbc.wechatredesign.mvp.views.SplashView

interface SplashPresenter: BasePresenter<SplashView> {

    fun onTapSignUpButton()
    fun onTapLoginButton()
}