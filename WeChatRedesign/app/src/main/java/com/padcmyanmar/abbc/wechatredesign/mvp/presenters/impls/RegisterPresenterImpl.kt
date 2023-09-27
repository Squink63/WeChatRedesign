package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.data.models.AuthenticationModel
import com.padcmyanmar.abbc.wechatredesign.data.models.AuthenticationModelImpl
import com.padcmyanmar.abbc.wechatredesign.data.models.UserModel
import com.padcmyanmar.abbc.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.RegisterPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.RegisterView

class RegisterPresenterImpl: RegisterPresenter, AbstractBasePresenter<RegisterView>() {

    private var mAuthModel: AuthenticationModel = AuthenticationModelImpl
    private var mUserModel: UserModel = UserModelImpl

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapAddProfilePicture() {
        mView.openGallery()
    }

    override fun onTapSignUpButton(bitmap: Bitmap, user: UserVO) {
        if (user.userName.isEmpty() || user.password.isEmpty()) {
            mView.showError("Please enter all fields")
        } else {
            mAuthModel.register(
                email = user.email,
                userName = user.userName,
                password = user.password,
                profilePicture = user.profilePicture,
                phoneNumber = user.phoneNumber,
                birthDate = user.birthDate,
                gender = user.gender,
                onSuccess = {
                    mUserModel.addUser(it)
                    mUserModel.uploadAndUpdateProfilePicture(bitmap, it)
                    mView.navigateToLoginScreen()
                },
                onFailure = {
                    mView.showError(it)
                }
            )
        }

    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }
}