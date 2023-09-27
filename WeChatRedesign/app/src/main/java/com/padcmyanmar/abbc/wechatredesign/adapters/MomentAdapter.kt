package com.padcmyanmar.abbc.wechatredesign.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.delegates.MomentDelegate
import com.padcmyanmar.abbc.wechatredesign.viewholders.MomentViewHolder

class MomentAdapter(private val delegate: MomentDelegate): BaseRecyclerAdapter<MomentViewHolder, MomentVO>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_moment, parent, false)
        return MomentViewHolder(view, delegate)
    }


}