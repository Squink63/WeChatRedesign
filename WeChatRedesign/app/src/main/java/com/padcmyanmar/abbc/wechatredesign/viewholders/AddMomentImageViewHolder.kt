package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.delegates.AddMomentImageDelegate
import kotlinx.android.synthetic.main.view_holder_add_moment_image.view.*
import java.text.FieldPosition

class AddMomentImageViewHolder(itemView: View, private val delegate: AddMomentImageDelegate) : RecyclerView.ViewHolder(itemView) {

    fun bindData(imageUrl: String, position: Int, itemCount: Int) {

        itemView.setOnClickListener {
            if (adapterPosition == itemCount - 1) {
                delegate.onTapAddImage()
            }
        }
        if (position == -1) {
            val image = imageUrl.toInt()
            itemView.ivAddMomentImage.setImageResource(image)
        } else {
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(itemView.ivAddMomentImage)
        }
    }
}