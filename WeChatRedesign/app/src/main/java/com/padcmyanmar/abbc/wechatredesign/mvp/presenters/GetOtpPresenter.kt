package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import com.padcmyanmar.abbc.wechatredesign.mvp.views.GetOtpView

interface GetOtpPresenter: BasePresenter<GetOtpView> {
    fun onTapBackButton()
    fun onTapVerifyButton()
}