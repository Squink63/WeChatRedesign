package com.padcmyanmar.abbc.wechatredesign.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.viewholders.ChatDetailImageViewHolder

class ChatImageAdapter: RecyclerView.Adapter<ChatDetailImageViewHolder>() {

    private var mImageList:List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatDetailImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_detail_image,parent,false)
        return ChatDetailImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mImageList.size
    }

    override fun onBindViewHolder(holder: ChatDetailImageViewHolder, position: Int) {
        holder.bindNewData(mImageList[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(imageList:List<String>) {
        mImageList = imageList
        notifyDataSetChanged()
    }
}