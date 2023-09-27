package com.padcmyanmar.abbc.wechatredesign.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.padcmyanmar.abbc.wechatredesign.delegates.FirstLetterItemDelegate
import kotlinx.android.synthetic.main.view_holder_first_letter.view.*

class FirstLetterViewHolder(itemView: View, private val delegate: FirstLetterItemDelegate) : RecyclerView.ViewHolder(itemView) {

    fun bindData(alphabet: Any) {

        itemView.tvFirstLetter.text = alphabet.toString()
    }
}