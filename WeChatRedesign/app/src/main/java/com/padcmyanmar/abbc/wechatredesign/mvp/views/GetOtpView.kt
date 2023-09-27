package com.padcmyanmar.abbc.wechatredesign.mvp.views

interface GetOtpView: BaseView {

    fun navigateToBackScreen()
    fun navigateToRegisterScreen()
    fun showOtp(otp: String)
}