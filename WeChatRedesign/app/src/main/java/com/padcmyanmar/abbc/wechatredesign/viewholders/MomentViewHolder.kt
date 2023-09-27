package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.adapters.MomentImageAdapter
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.delegates.MomentDelegate
import kotlinx.android.synthetic.main.view_holder_moment.view.*

class MomentViewHolder(itemView: View, private val delegate: MomentDelegate) : AbstractBaseViewHolder<MomentVO>(itemView) {

    private lateinit var mAdapter: MomentImageAdapter
    private var mMoment: MomentVO? = null

    init {
        itemView.ivBookmark.setOnClickListener {
            if (mMoment?.isBookmarked == true) {
                delegate.onTapBookmarkButton( mMoment?.id ?: "", false)
            }else {
                delegate.onTapBookmarkButton(mMoment?.id ?: "", true)
            }
        }
    }

    override fun bindData(data: MomentVO) {

        mMoment = data
        itemView.tvUploaderName.text = data.userName
        itemView.tvMomentText.text = data.caption

        Glide.with(itemView.context)
            .load(data.userProfileImage)
            .into(itemView.ivUploaderImage)

        setUpMomentImages()
        mAdapter.setNewData(changeImageStringToList(data.imageUrl))

        if (data.isBookmarked) {
            itemView.ivBookmark.setImageResource(R.drawable.bookmark_fill)
        } else {
            itemView.ivBookmark.setImageResource(R.drawable.bookmark_light)
        }
    }

    private fun changeImageStringToList(imageString:String) : List<String> {
        return imageString.split(',').toList()
    }

    private fun setUpMomentImages() {
        mAdapter = MomentImageAdapter()
        itemView.vpMomentImage.adapter = mAdapter
    }
}