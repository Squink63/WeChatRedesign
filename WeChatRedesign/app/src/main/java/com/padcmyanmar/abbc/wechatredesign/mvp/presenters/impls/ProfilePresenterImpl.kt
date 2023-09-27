package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.data.models.*
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.ProfilePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ProfileView

class ProfilePresenterImpl: ProfilePresenter, AbstractBasePresenter<ProfileView>() {

    override var mAuthModel: AuthenticationModel = AuthenticationModelImpl

    override var mUserModel: UserModel = UserModelImpl

    override var mMomentModel: MomentModel = MomentModelImpl


    override fun onTapEditProfileButton() {
        mView.showEditProfileDialog()
    }

    override fun onTapProfilePicture() {
        mView.openGallery()
    }

    override fun onTapQRCode() {
        mView.showQRCode()
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun updateUserInfo(user: UserVO) {
        mUserModel.addUser(user)
    }

    override fun uploadAndUpdateProfilePicture(bitmap: Bitmap, user: UserVO) {
        mUserModel.uploadAndUpdateProfilePicture(bitmap, user)
    }

    override fun createMoment(moment: MomentVO) {
        mMomentModel.createMoment(moment)
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mUserModel.getUser(
            onSuccess = { mView.showUserInfo(it) },
            onFailure = { mView.showError(it) }
        )
    }

    override fun onTapBookmarkButton(id: String, isBookmarked: Boolean) {
        mView.getMomentIsBookmarked(id, isBookmarked)
    }
}