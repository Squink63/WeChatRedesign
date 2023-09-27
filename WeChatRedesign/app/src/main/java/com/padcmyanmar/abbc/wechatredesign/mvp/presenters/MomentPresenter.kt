package com.padcmyanmar.abbc.wechatredesign.mvp.presenters

import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.delegates.MomentDelegate
import com.padcmyanmar.abbc.wechatredesign.mvp.views.MomentView

interface MomentPresenter: BasePresenter<MomentView>, MomentDelegate {

    fun onTapCreateMomentButton()
    fun createMoment(moment: MomentVO)
    fun getUserId(): String
}