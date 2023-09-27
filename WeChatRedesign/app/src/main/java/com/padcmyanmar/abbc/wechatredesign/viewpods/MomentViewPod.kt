package com.padcmyanmar.abbc.wechatredesign.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.abbc.wechatredesign.adapters.MomentAdapter
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.delegates.MomentDelegate
import kotlinx.android.synthetic.main.view_pod_moment.view.*

class MomentViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var mAdapter: MomentAdapter

    private lateinit var mDelegate: MomentDelegate

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    fun setUpMomentViewPod(delegate: MomentDelegate) {
        this.mDelegate = delegate
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mAdapter = MomentAdapter(mDelegate)
        rvMomentList.adapter = mAdapter
        rvMomentList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    fun setNewData(momentList: List<MomentVO>) {
        mAdapter.setNewData(momentList)
    }
}