package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.data.models.*
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.CreateMomentPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.CreateMomentView

class CreateMomentPresenterImpl: CreateMomentPresenter, AbstractBasePresenter<CreateMomentView>() {


    override var mAuthModel: AuthenticationModel = AuthenticationModelImpl

    override var mMomentModel: MomentModel = MomentModelImpl

    override var mUserModel: UserModel = UserModelImpl


    override fun onTapAddImage() {
        mView.openGallery()
    }

    override fun onTapBackButton() {
        mView.navigateToBackScreen()
    }

    override fun onTapCreateButton(moment: MomentVO) {
        mMomentModel.createMoment(moment)
    }

    override fun createMomentImages(bitmap: Bitmap) {
        mMomentModel.updateAndUploadMomentImage(bitmap)
    }

    override fun getMomentImages(): String {
        return mMomentModel.getMomentImages()
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mUserModel.getUser(
            onSuccess = { mView.showUserInformation(it) },
            onFailure = { mView.showError(it) }
        )
    }
}