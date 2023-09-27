package com.padcmyanmar.abbc.wechatredesign.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.activities.CreateMomentActivity
import com.padcmyanmar.abbc.wechatredesign.adapters.MomentAdapter
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.MomentPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.MomentPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.MomentView
import com.padcmyanmar.abbc.wechatredesign.viewpods.MomentViewPod
import kotlinx.android.synthetic.main.fragment_moment.*

class MomentFragment : Fragment() , MomentView {

    private lateinit var mViewPod: MomentViewPod
    private lateinit var mPresenter: MomentPresenter
    private var mMomentList: List<MomentVO> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()

        setUpViewPod()
        setUpActionListeners()

        mPresenter.onUiReady(requireActivity(), this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MomentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpActionListeners() {
        btnCreateMoment.setOnClickListener {
            mPresenter.onTapCreateMomentButton()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun setUpViewPod() {
        mViewPod = vpMoment as MomentViewPod
        mViewPod.setUpMomentViewPod(mPresenter)
    }

    override fun navigateToCreateMomentScreen() {
        startActivity(CreateMomentActivity.newIntent(requireContext()))
    }

    override fun showMoments(moment: List<MomentVO>) {
        mMomentList = moment
        mViewPod.setNewData(moment)
    }

    override fun getMomentIsBookmarked(id: String, isBookmarked: Boolean) {
        for (moment in mMomentList) {
            if (id == moment.id) {
                if (isBookmarked) {
                    moment.isBookmarked = true
                    mPresenter.createMoment(moment)
                    break
                } else {
                    moment.isBookmarked = false
                    mPresenter.createMoment(moment)
                    break
                }
            }
        }
        mViewPod.setNewData(mMomentList)
    }

    override fun showError(error: String) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show()
    }


}