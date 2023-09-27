package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.models.AuthenticationModel
import com.padcmyanmar.abbc.wechatredesign.data.models.MomentModel
import com.padcmyanmar.abbc.wechatredesign.data.models.UserModel
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.delegates.AddMomentImageDelegate
import com.padcmyanmar.abbc.wechatredesign.mvp.views.CreateMomentView

interface CreateMomentPresenter: BasePresenter<CreateMomentView>, AddMomentImageDelegate {

    var mAuthModel: AuthenticationModel
    var mMomentModel: MomentModel
    var mUserModel: UserModel

    fun onTapBackButton()
    fun onTapCreateButton(moment: MomentVO)
    fun createMomentImages(bitmap: Bitmap)
    fun getMomentImages(): String
    fun getUserId(): String
}