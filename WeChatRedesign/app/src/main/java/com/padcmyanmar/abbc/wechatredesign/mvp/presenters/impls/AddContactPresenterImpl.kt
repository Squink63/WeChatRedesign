package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.data.models.AuthenticationModel
import com.padcmyanmar.abbc.wechatredesign.data.models.AuthenticationModelImpl
import com.padcmyanmar.abbc.wechatredesign.data.models.UserModel
import com.padcmyanmar.abbc.wechatredesign.data.models.UserModelImpl
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AddContactPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.AddContactView

class AddContactPresenterImpl: AddContactPresenter, AbstractBasePresenter<AddContactView>() {

    private val mUSerModel: UserModel = UserModelImpl
    private val mAuthModel: AuthenticationModel = AuthenticationModelImpl

    override fun getUsers(exporterUserId: String) {
        mUSerModel.getUser(
            onSuccess = {mView.getUsers(it, exporterUserId)},
            onFailure = {mView.showError(it)}
        )
    }

    override fun createContact(scannerId: String, exporterId: String, contact: UserVO) {
        mUSerModel.addContact(scannerId, exporterId, contact)
    }

    override fun getScannerUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }
}