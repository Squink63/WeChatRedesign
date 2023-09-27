package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractBaseViewHolder<W>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    protected var mData: W? = null

    abstract fun bindData(data: W)
}