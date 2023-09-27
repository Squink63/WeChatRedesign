package com.padcmyanmar.abbc.wechatredesign.data.models

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.network.CloudFirestoreFirebaseApi

interface UserModel {

    var mFirebaseApi: CloudFirestoreFirebaseApi

    fun addUser(user: UserVO)
    fun uploadAndUpdateProfilePicture(bitmap: Bitmap, user: UserVO)
    fun getUser(onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit)
    fun addContact(scannerId: String, exporterId:String, contact: UserVO)
    fun getContacts(scannerId: String, onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit)
}