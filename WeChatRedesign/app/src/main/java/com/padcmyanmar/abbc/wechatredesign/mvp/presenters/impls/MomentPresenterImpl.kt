package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.data.models.AuthenticationModel
import com.padcmyanmar.abbc.wechatredesign.data.models.AuthenticationModelImpl
import com.padcmyanmar.abbc.wechatredesign.data.models.MomentModel
import com.padcmyanmar.abbc.wechatredesign.data.models.MomentModelImpl
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.MomentPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.MomentView

class MomentPresenterImpl : MomentPresenter, AbstractBasePresenter<MomentView>() {

    private val mMomentModel: MomentModel = MomentModelImpl
    private val mAuthModel: AuthenticationModel = AuthenticationModelImpl

    override fun onTapBookmarkButton(id: String, isBookmarked: Boolean) {
        mView.getMomentIsBookmarked(id, isBookmarked)
    }

    override fun onTapCreateMomentButton() {
        mView.navigateToCreateMomentScreen()
    }

    override fun createMoment(moment: MomentVO) {
        mMomentModel.createMoment(moment)
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mMomentModel.getMoments(
            onSuccess = { mView.showMoments(it)},
            onFailure = { mView.showError(it)}
        )
    }
}