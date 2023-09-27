package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.SplashPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.SplashView

class SplashPresenterImpl: SplashPresenter, AbstractBasePresenter<SplashView>() {
    override fun onTapSignUpButton() {
        mView.navigateToGetOtpScreen()
    }

    override fun onTapLoginButton() {
        mView.navigateToLoginScreen()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }

}