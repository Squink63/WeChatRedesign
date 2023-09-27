package com.padcmyanmar.abbc.wechatredesign.data.models

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.network.CloudFirestoreFirebaseApi
import com.padcmyanmar.abbc.wechatredesign.network.CloudFirestoreFirebaseImpl

object UserModelImpl: UserModel {
    override var mFirebaseApi: CloudFirestoreFirebaseApi = CloudFirestoreFirebaseImpl


    override fun addUser(user: UserVO) {
        mFirebaseApi.addUser(user)
    }

    override fun uploadAndUpdateProfilePicture(bitmap: Bitmap, user: UserVO) {
        mFirebaseApi.uploadAndUpdateProfilePicture(bitmap, user)
    }

    override fun getUser(onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getUser(onSuccess, onFailure)
    }

    override fun addContact(scannerId: String, exporterId: String, contact: UserVO) {
        mFirebaseApi.addContact(scannerId, exporterId, contact)
    }

    override fun getContacts(
        scannerId: String,
        onSuccess: (users: List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getContacts(scannerId, onSuccess, onFailure)
    }
}