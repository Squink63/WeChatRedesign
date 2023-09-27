package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.models.AuthenticationModel
import com.padcmyanmar.abbc.wechatredesign.data.models.MomentModel
import com.padcmyanmar.abbc.wechatredesign.data.models.UserModel
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.delegates.MomentDelegate
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ProfileView

interface ProfilePresenter : BasePresenter<ProfileView>, MomentDelegate {

    var mAuthModel: AuthenticationModel
    var mUserModel: UserModel
    var mMomentModel: MomentModel


    fun onTapEditProfileButton()
    fun onTapProfilePicture()
    fun onTapQRCode()
    fun getUserId(): String
    fun updateUserInfo(user: UserVO)
    fun uploadAndUpdateProfilePicture(bitmap: Bitmap, user: UserVO)
    fun createMoment(moment: MomentVO)
}