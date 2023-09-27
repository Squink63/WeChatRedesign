package com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import com.padcmyanmar.abbc.wechatredesign.data.models.*
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AbstractBasePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.ChatDetailPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ChatDetailView

class ChatDetailPresenterImpl: ChatDetailPresenter, AbstractBasePresenter<ChatDetailView>() {

    private val mAuthModel: AuthenticationModel = AuthenticationModelImpl
    private val mUserModel: UserModel = UserModelImpl
    private val mChatModel: ChatModel = ChatModelImpl

    override fun onUiReady(context: Context, owner: LifecycleOwner) {
        mUserModel.getUser(
            onSuccess = {
                mView.showUsers(it)
            },
            onFailure = {
                mView.showError(it)
            }
        )

        mChatModel.getGroups(
            onSuccess = {
                mView.getGroups(it)
            },
            onFailure = {
                mView.showError(it)
            }
        )
    }

    override fun onTapGetImageButton() {
        mView.openGallery()
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun sendMessage(
        senderId: String,
        receiverId: String,
        timeStamp: Long,
        message: MessageVO
    ) {
        mChatModel.sendMessage(senderId, receiverId, timeStamp, message)
    }

    override fun getMessages(senderId: String, receiverId: String) {
        mChatModel.getMessages(
            senderId,
            receiverId,
            onSuccess = { messageList ->
                val sortedMessages = messageList.sortedBy { it.timeStamp }
                mView.showMessages(sortedMessages)
            },
            onFailure = {
                mView.showError(it)
            }
        )
    }

    override fun uploadAndSendImage(bitmap: Bitmap) {
        mChatModel.uploadAndSendImage(
            bitmap,
            onSuccess = {
                mView.getImageUrlForFile(it)
            },
            onFailure = {
                mView.showError(it)
            }
        )
    }

    override fun sendGroupMessage(groupId: Long, timeStamp: Long, message: MessageVO) {
        mChatModel.sendGroupMessage(groupId, timeStamp, message)
    }

    override fun getGroupMessages(groupId: Long) {
        mChatModel.getGroupMessages(
            groupId,
            onSuccess = { messageList ->
                val sortedMessages = messageList.sortedBy { it.timeStamp }
                mView.showGroupMessages(sortedMessages)
            },
            onFailure = {
                mView.showError(it)
            }
        )
    }
}