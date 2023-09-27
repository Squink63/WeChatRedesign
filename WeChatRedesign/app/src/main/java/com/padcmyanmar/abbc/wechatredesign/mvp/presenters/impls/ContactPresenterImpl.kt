package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.data.models.*
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.ContactPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ContactView

class ContactPresenterImpl: ContactPresenter, AbstractBasePresenter<ContactView>() {

    private var mAuthModel: AuthenticationModel = AuthenticationModelImpl
    private var mUserModel: UserModel = UserModelImpl
    private var mChatModel: ChatModel = ChatModelImpl

    override fun onTapAddContactButton() {
        mView.navigateToAddContactScreen()
    }

    override fun onTapAddNewGroupButton() {
        mView.navigateToAddGroupScreen()
    }

    override fun getContacts(scannerId: String) {
        mUserModel.getContacts(
            scannerId,
            onSuccess = { mView.showContacts(it)},
            onFailure = {mView.showError(it)}
        )

        mChatModel.getGroups(
            onSuccess = { mView.getGroupList(it) },
            onFailure = { mView.showError(it)}
        )

    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun onUiReady(context: Context, owner: LifecycleOwner) {

    }

    override fun onTapGroupItem(groupId: Long) {
        mView.navigateToChatDetailScreenFromGroup(groupId.toString())
    }

    override fun onTapLetter(position: Int) {

    }

    override fun onTapChatItem(userId: String) {
       mView.navigateToChatDetailScreen(userId)
    }

    override fun onTapCheckbox(userId: String, isChecked: Boolean) {

    }


}