package com.padcmyanmar.abbc.wechatredesign.mvp.views

import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO

interface MomentView: BaseView {

    fun navigateToCreateMomentScreen()
    fun showMoments(moment: List<MomentVO>)
    fun getMomentIsBookmarked(id: String, isBookmarked: Boolean)
}