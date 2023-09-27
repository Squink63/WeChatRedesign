package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.data.models.ChatModel
import com.padcmyanmar.abbc.wechatredesign.data.models.ChatModelImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.GetOtpPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.GetOtpView

class GetOtpPresenterImpl: GetOtpPresenter, AbstractBasePresenter<GetOtpView>() {

    private var mChatModel : ChatModel = ChatModelImpl

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapVerifyButton() {
        mView.navigateToRegisterScreen()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

        mChatModel.getOtp(
            onSuccess = {mView.showOtp(it)},
            onFailure = {mView.showError(it)}
        )
    }
}