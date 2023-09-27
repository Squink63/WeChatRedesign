package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.data.models.*
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AddGroupPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.AddGroupView

class AddGroupPresenterImpl: AddGroupPresenter, AbstractBasePresenter<AddGroupView>() {

    private val mAuthModel: AuthenticationModel = AuthenticationModelImpl
    private val mUserModel: UserModel = UserModelImpl
    private val mChatModel: ChatModel = ChatModelImpl

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
    }

    override fun onTapLetter(position: Int) {

    }

    override fun getContacts(scannerId: String) {
        mUserModel.getContacts(
            scannerId,
            onSuccess = { mView.showContacts(it) },
            onFailure = { mView.showError(it) }
        )
    }



    override fun onTapChatItem(userId: String) {
        mView.navigateToChatDetailScreen(userId)
    }

    override fun onTapCheckbox(userId: String, isChecked: Boolean) {
        mView.addUserToGroup(userId, isChecked)
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun onTapCreateButton(
        timeStamp: Long,
        groupName: String,
        userList: List<String>,
        imageUrl: String
    ) {
        mChatModel.addGroup(timeStamp, groupName, userList, imageUrl)
    }

    override fun onTapProfileImage() {
        mView.openGallery()
    }

    override fun uploadGroupCoverPhoto(timeStamp: Long, bitmap: Bitmap) {
        mChatModel.uploadGroupCoverPhoto(
            timeStamp,
            bitmap,
            onSuccess = {mView.getGroupCoverImageUrl(it)},
            onFailure = {mView.showError(it)}
        )
    }
}