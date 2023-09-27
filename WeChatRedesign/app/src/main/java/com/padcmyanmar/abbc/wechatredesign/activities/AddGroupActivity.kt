package com.padcmyanmar.abbc.wechatredesign.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.adapters.MemberAdapter
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AddGroupPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.AddGroupPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.AddGroupView
import com.padcmyanmar.abbc.wechatredesign.viewpods.ContactViewPod
import kotlinx.android.synthetic.main.activity_add_group.*
import kotlinx.android.synthetic.main.activity_register.*
import java.io.IOException

class AddGroupActivity : BaseActivity(), AddGroupView {

    private lateinit var mAdapter: MemberAdapter
    private lateinit var mViewPod: ContactViewPod
    private lateinit var mPresenter: AddGroupPresenter
    private val IMAGE_REQUEST_CODE = 6666

    private var mMemberList: ArrayList<UserVO> = arrayListOf()
    private var mUserList: List<UserVO> = listOf()
    private var mGroupCoverImageUrl: String = ""

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, AddGroupActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)

        setUpPresenter()

        setUpRecyclerView()
        setUpViewPod()
        setUpActionListeners()
    }

    private fun setUpPresenter() {

        mPresenter = getPresenter<AddGroupPresenterImpl, AddGroupView>()
    }

    private fun setUpActionListeners() {
        btnDismiss.setOnClickListener {
            finish()
        }

        btnCreateGroup.setOnClickListener {
            val mUserIdList = arrayListOf<String>()
            val groupName = etGroupName.text.toString()
            for(member in mMemberList) {
                mUserIdList.add(member.userId)
            }
            mUserIdList.add(mPresenter.getUserId())
            if(groupName.isNotEmpty() && mGroupCoverImageUrl.isNotEmpty()) {
                mPresenter.onTapCreateButton(System.currentTimeMillis(),groupName,mUserIdList.toList(), mGroupCoverImageUrl)
                finish()
            }
        }

        ivGroupCoverPhoto.setOnClickListener {
            mPresenter.onTapProfileImage()
        }
    }

    private fun setUpViewPod() {

        mViewPod = vpContactGroup as ContactViewPod
        mViewPod.setUpContactViewPod(mPresenter, mPresenter)
        mPresenter.getContacts(mPresenter.getUserId())
    }

    private fun setUpRecyclerView() {
        mAdapter = MemberAdapter()
        rvMemberList.adapter = mAdapter
        rvMemberList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun navigateToChatDetailScreen(userId: String) {
        startActivity(ChatDetailActivity.newIntent(this, userId, ""))
    }

    private fun getFirstLetter(userNameList: List<String>) : List<Char> {
        val userNameMapList = userNameList.groupBy { it[0] }
        val firstLetterList = arrayListOf<Char>()
        for (key in userNameMapList.keys) {
            firstLetterList.add(key)
        }
        return firstLetterList.sorted()
    }

    override fun showContacts(userList: List<UserVO>) {
        mUserList = userList
        val nameList = arrayListOf<String>()
        for (contact in userList) {
            nameList.add(0, contact.userName)
        }
        mViewPod.setNewData(getFirstLetter(nameList), userList, true)
    }

    override fun addUserToGroup(userId: String, isChecked: Boolean) {
        if(isChecked) {
            for(user in mUserList) {
                if(userId == user.userId) {
                    mMemberList.add(user)
                    Log.d("NewGroup","$mMemberList")
                }
            }
        } else {
            for(user in mUserList) {
                if(userId == user.userId) {
                    mMemberList.remove(user)
                }
            }
        }

        mAdapter.setNewData(mMemberList)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {

            val filePath = data?.data
            try {
                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(this.contentResolver, filePath)

                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        mPresenter.uploadGroupCoverPhoto(System.currentTimeMillis(), bitmapImage)
                        ivGroupCoverPhoto.setImageBitmap(bitmapImage)
                    }else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, filePath
                        )
                        mPresenter.uploadGroupCoverPhoto(System.currentTimeMillis(), bitmapImage)
                        ivGroupCoverPhoto.setImageBitmap(bitmapImage)
                    }
                }
            }catch (e : IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Image Selected"), IMAGE_REQUEST_CODE)
    }

    override fun getGroupCoverImageUrl(imageUrl: String) {
        mGroupCoverImageUrl = imageUrl
    }
}