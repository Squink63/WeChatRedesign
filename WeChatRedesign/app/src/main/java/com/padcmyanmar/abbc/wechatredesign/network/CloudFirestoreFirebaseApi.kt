package com.padcmyanmar.abbc.wechatredesign.network

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO

interface CloudFirestoreFirebaseApi {

    fun addUser(user: UserVO)
    fun uploadAndUpdateProfilePicture(bitmap: Bitmap, user: UserVO)
    fun getUser(onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit)
    fun createMoment(moment: MomentVO)
    fun updateAndUploadMomentImage(bitmap: Bitmap)
    fun getMomentImages(): String
    fun getMoments(onSuccess: (moments: List<MomentVO>) -> Unit, onFailure: (String) -> Unit)
    fun addContact(scannerId: String, exporterId:String, contact: UserVO)
    fun getContacts(scannerId: String, onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit)
}