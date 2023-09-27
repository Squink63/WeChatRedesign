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
import com.padcmyanmar.abbc.wechatredesign.activities.ChatDetailActivity
import com.padcmyanmar.abbc.wechatredesign.adapters.ActiveNowAdapter
import com.padcmyanmar.abbc.wechatredesign.adapters.ChatListAdapter
import com.padcmyanmar.abbc.wechatredesign.adapters.GroupChatAdapter
import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.ChatPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.ChatPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ChatView
import kotlinx.android.synthetic.main.fragment_chat.*


class ChatFragment : Fragment(), ChatView {

    private lateinit var mAdapter:ActiveNowAdapter
    private lateinit var mChatAdapter: ChatListAdapter
    private lateinit var mGroupChatAdapter: GroupChatAdapter

    private lateinit var mPresenter: ChatPresenter
    private var mUserList: List<UserVO> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()

        setUpAdapters()

        mPresenter.onUiReady(requireContext(), this)
        mPresenter.getContacts(mPresenter.getUserId())
        mPresenter.getChatHistoryUserId(mPresenter.getUserId())

    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ChatPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpAdapters() {
        mAdapter = ActiveNowAdapter(mPresenter)
        rvActiveNow.adapter = mAdapter
        rvActiveNow.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mChatAdapter = ChatListAdapter(mPresenter)
        rvChatList.adapter = mChatAdapter
        rvChatList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        mGroupChatAdapter = GroupChatAdapter(mPresenter)
        rvGroupChatList.adapter = mGroupChatAdapter
        rvGroupChatList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun navigateToChatDetailScreen(userId: String) {
        startActivity(ChatDetailActivity.newIntent(requireContext(), userId, ""))
    }

    override fun showContacts(contactList: List<UserVO>) {
        mAdapter.setNewData(contactList)
    }

    override fun showUserId(userIdList: List<String>) {
        val chatUserList = arrayListOf<UserVO>()
        for (userId in userIdList) {
            for (user in mUserList) {
                if (userId == user.userId) {
                    chatUserList.add(user)
                    break
                }
            }
        }
        mChatAdapter.setNewData(chatUserList)
    }

    override fun getUsers(userList: List<UserVO>) {
        mUserList = userList
    }

    override fun getGroups(groupList: List<GroupVO>) {
        for(group in groupList) {

            for(userId in group.userIdList) {
                if(mPresenter.getUserId() == userId) {
                    mPresenter.getGroupMessages(
                        groupId = group.id,
                        onSuccess = {
                            if(it > 0) {
                                mGroupChatAdapter.setNewData(group)
                            }
                        }
                    )
                    break
                }
            }
        }
    }

    override fun navigateToGroupChatDetailScreen(groupId: Long) {
        startActivity(ChatDetailActivity.newIntent(requireContext(), "", groupId.toString()))
    }

    override fun showError(error: String) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_LONG).show()
    }

}