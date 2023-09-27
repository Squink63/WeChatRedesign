package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.views.RegisterView

interface RegisterPresenter: BasePresenter<RegisterView> {

    fun onTapBackButton()
    fun onTapAddProfilePicture()
    fun onTapSignUpButton(bitmap: Bitmap, user: UserVO)
}