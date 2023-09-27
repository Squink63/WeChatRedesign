package com.padcmyanmar.abbc.wechatredesign.data.models

import android.graphics.Bitmap
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.network.CloudFirestoreFirebaseApi
import com.padcmyanmar.abbc.wechatredesign.network.CloudFirestoreFirebaseImpl

object MomentModelImpl: MomentModel {

    override var mFirebaseApi: CloudFirestoreFirebaseApi = CloudFirestoreFirebaseImpl

    override fun createMoment(moment: MomentVO) {
        mFirebaseApi.createMoment(moment)
    }

    override fun updateAndUploadMomentImage(bitmap: Bitmap) {
        mFirebaseApi.updateAndUploadMomentImage(bitmap)
    }

    override fun getMomentImages(): String {
        return mFirebaseApi.getMomentImages()
    }

    override fun getMoments(onSuccess: (moments: List<MomentVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getMoments(onSuccess, onFailure)
    }
}