package com.padcmyanmar.abbc.wechatredesign.data.models

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.network.CloudFirestoreFirebaseApi

interface MomentModel {

    var mFirebaseApi: CloudFirestoreFirebaseApi

    fun createMoment(moment: MomentVO)
    fun updateAndUploadMomentImage(bitmap: Bitmap)

    fun getMomentImages()  : String
    fun getMoments(onSuccess: (moments: List<MomentVO>) -> Unit, onFailure: (String) -> Unit)
}