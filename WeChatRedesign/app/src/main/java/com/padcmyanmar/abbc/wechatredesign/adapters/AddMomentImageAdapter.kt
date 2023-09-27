package com.padcmyanmar.abbc.wechatredesign.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.delegates.AddMomentImageDelegate
import com.padcmyanmar.abbc.wechatredesign.viewholders.AddMomentImageViewHolder

class AddMomentImageAdapter(private val delegate: AddMomentImageDelegate): RecyclerView.Adapter<AddMomentImageViewHolder>() {

    private var imageList = mutableListOf(R.drawable.frame_image.toString())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddMomentImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_add_moment_image, parent, false)
        return AddMomentImageViewHolder(view, delegate)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: AddMomentImageViewHolder, position: Int) {

        if (position == imageList.lastIndex) {
            holder.bindData(imageList[position], -1,itemCount)
        } else {
            holder.bindData(imageList[position], position, itemCount)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(image: String) {
        imageList.add(0, image)
        notifyDataSetChanged()
    }
}