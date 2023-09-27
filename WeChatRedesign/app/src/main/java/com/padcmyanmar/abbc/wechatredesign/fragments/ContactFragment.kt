package com.padcmyanmar.abbc.wechatredesign.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.activities.AddContactActivity
import com.padcmyanmar.abbc.wechatredesign.activities.AddGroupActivity
import com.padcmyanmar.abbc.wechatredesign.activities.ChatDetailActivity
import com.padcmyanmar.abbc.wechatredesign.adapters.GroupListAdapter
import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.ContactPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.ContactPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ContactView
import com.padcmyanmar.abbc.wechatredesign.viewpods.ContactViewPod
import kotlinx.android.synthetic.main.fragment_contact.*


class ContactFragment : Fragment(), ContactView {


    private lateinit var mGroupAdapter: GroupListAdapter
    private lateinit var mViewPod: ContactViewPod
    private lateinit var mPresenter: ContactPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()

        setUpRecyclerView()
        setUpViewPod()
        setUpActionListeners()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ContactPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpRecyclerView() {

        mGroupAdapter = GroupListAdapter(mPresenter)
        rvGroupList.adapter = mGroupAdapter
        rvGroupList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

    }

    private fun setUpActionListeners() {
        btnAddContact.setOnClickListener {
            mPresenter.onTapAddContactButton()
        }

        btnAddNewGroup.setOnClickListener {
            mPresenter.onTapAddNewGroupButton()
        }

    }

    private fun setUpViewPod() {

        mViewPod = vpContact as ContactViewPod
        mViewPod.setUpContactViewPod(mPresenter, mPresenter)
        mPresenter.getContacts(mPresenter.getUserId())
    }

    override fun navigateToAddContactScreen() {
        startActivity(AddContactActivity.newIntent(requireContext()))
    }

    override fun navigateToAddGroupScreen() {
        startActivity(AddGroupActivity.newIntent(requireContext()))
    }

    override fun navigateToChatDetailScreen(userId: String) {
        startActivity(ChatDetailActivity.newIntent(requireContext(), userId, ""))
    }

    private fun getFirstLetter(userNameList: List<String>) : List<Char> {
        val userNameMapList = userNameList.groupBy { it[0] }
        val firstLetterList = arrayListOf<Char>()
        for (key in userNameMapList.keys) {
            firstLetterList.add(key)
        }
        return firstLetterList.sorted()
    }

    override fun showContacts(contactList: List<UserVO>) {
        val userNameList = arrayListOf<String>()
        for (contact in contactList){
            userNameList.add(0, contact.userName)
        }
        mViewPod.setNewData(getFirstLetter(userNameList), contactList, false)
    }

    override fun addToGroup(userId: String) {

    }

    override fun getGroupList(groupList: List<GroupVO>) {
        val mGroupList = arrayListOf<GroupVO>()
        for (group in groupList) {
            if (mPresenter.getUserId() in group.userIdList) {
                mGroupList.add(group)
            }
        }
        mGroupAdapter.setNewData(mGroupList)
        val count = mGroupList.size.toString()
        tvGroup.text = "Group($count)"
    }

    override fun navigateToChatDetailScreenFromGroup(groupId: String) {
        startActivity(ChatDetailActivity.newIntent(requireContext(),"", groupId))
    }

    override fun showError(error: String) {
        Toast.makeText(requireActivity(),error, Toast.LENGTH_LONG).show()
    }

}