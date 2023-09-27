package com.padcmyanmar.abbc.wechatredesign.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.delegates.FirstLetterItemDelegate
import com.padcmyanmar.abbc.wechatredesign.viewholders.ChatListViewHolder
import com.padcmyanmar.abbc.wechatredesign.viewholders.FirstLetterViewHolder
import com.padcmyanmar.abbc.wechatredesign.viewholders.MomentViewHolder

class FirstLetterAdapter(
    private val letterList: List<Char>,
    private val delegate: FirstLetterItemDelegate
): RecyclerView.Adapter<FirstLetterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstLetterViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_first_letter, parent, false)
        return FirstLetterViewHolder(view, delegate)
    }

    override fun onBindViewHolder(holder: FirstLetterViewHolder, position: Int) {

        holder.bindData(letterList[position])
    }

    override fun getItemCount(): Int {
        return letterList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(letterList: List<Char>) {
        notifyDataSetChanged()
    }
}