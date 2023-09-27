package com.padcmyanmar.abbc.wechatredesign.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.abbc.wechatredesign.adapters.ContactListAdapter
import com.padcmyanmar.abbc.wechatredesign.adapters.FirstLetterAdapter
import com.padcmyanmar.abbc.wechatredesign.data.DummyData
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.delegates.ChatItemDelegate
import com.padcmyanmar.abbc.wechatredesign.delegates.FirstLetterItemDelegate
import kotlinx.android.synthetic.main.view_pod_contact.view.*

class ContactViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var mLetterAdapter: FirstLetterAdapter
    private lateinit var mLetterDelegate: FirstLetterItemDelegate
    private lateinit var mContactListAdapter: ContactListAdapter
    private lateinit var mChatDelegate: ChatItemDelegate

    override fun onFinishInflate() {
        super.onFinishInflate()

    }

    fun setUpContactViewPod(chatDelegate: ChatItemDelegate, delegateLetter: FirstLetterItemDelegate) {

        this.mChatDelegate = chatDelegate
        this.mLetterDelegate = delegateLetter

        setUpLetterAdapter()
        setUpContactListAdapter()
    }

    private fun setUpContactListAdapter() {
        mContactListAdapter = ContactListAdapter(mChatDelegate)
        rvContactList.adapter = mContactListAdapter
        rvContactList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    private fun setUpLetterAdapter() {

        mLetterAdapter = FirstLetterAdapter(DummyData.getFirstLetterList(), mLetterDelegate)
        rvFirstLetter.adapter = mLetterAdapter
        rvFirstLetter.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun setNewData(letterList: List<Char>, contactList: List<UserVO>, isGroup: Boolean) {
        mContactListAdapter.setNewData(letterList, contactList, isGroup)
    }
}