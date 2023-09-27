package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_holder_moment.view.*
import kotlinx.android.synthetic.main.view_holder_upload_image.view.*

class MomentImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindNewData(image: String) {
        Glide.with(itemView.context)
            .load(image)
            .into(itemView.ivUploadedImage)
    }
}