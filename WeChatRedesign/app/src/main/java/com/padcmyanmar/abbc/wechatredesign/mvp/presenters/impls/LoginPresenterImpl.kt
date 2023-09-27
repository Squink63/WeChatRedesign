package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.data.models.AuthenticationModel
import com.padcmyanmar.abbc.wechatredesign.data.models.AuthenticationModelImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.LoginPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.LoginView

class LoginPresenterImpl: LoginPresenter, AbstractBasePresenter<LoginView>() {

    private val mAuthModel: AuthenticationModel = AuthenticationModelImpl

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapLoginButton(email: String, password: String, phoneNumber: String) {
        if (email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            mView.showError("Please enter all the fields")
        } else {
            mAuthModel.login(
                email,password,phoneNumber,
                onSuccess = { mView.navigateToMainScreen()},
                onFailure = { mView.showError(it)}
            )
        }
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }
}