package com.padcmyanmar.abbc.wechatredesign.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.adapters.ChatImageAdapter
import com.padcmyanmar.abbc.wechatredesign.adapters.ChatMessageAdapter
import com.padcmyanmar.abbc.wechatredesign.data.vos.GroupVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.MessageVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.ChatDetailPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.ChatDetailPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ChatDetailView
import kotlinx.android.synthetic.main.activity_chat_detail.*
import kotlinx.android.synthetic.main.activity_chat_detail.btnBack
import kotlinx.android.synthetic.main.activity_register.*
import java.io.IOException

class ChatDetailActivity : BaseActivity(), ChatDetailView {

    private lateinit var mChatMessageAdapter: ChatMessageAdapter
    private lateinit var mImageAdapter: ChatImageAdapter
    private lateinit var mPresenter: ChatDetailPresenter

    private var mUserName: String = ""
    private var mUserProfileImage: String = ""
    private var mReceiverId = ""
    private var mGroupId = ""
    private var mGroupName = ""

    private val IMAGE_REQUEST_CODE = 6666
    private val IMAGE_CAPTURE_CODE = 3333
    private var mImageList: ArrayList<String> = arrayListOf()


    companion object{
        private const val EXTRA_USER_ID = "EXTRA_USER_ID"
        private const val EXTRA_GROUP_ID = "EXTRA_GROUP_ID"
        fun newIntent(context: Context, userId: String, groupId: String): Intent {
            val intent = Intent(context, ChatDetailActivity::class.java)
            intent.putExtra(EXTRA_USER_ID, userId)
            intent.putExtra(EXTRA_GROUP_ID, groupId)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_detail)

        setUpPresenter()

        setUpRecyclerView()

        mReceiverId = intent.getStringExtra(EXTRA_USER_ID) ?: ""
        mGroupId = intent.getStringExtra(EXTRA_GROUP_ID) ?: ""
        setUpImageRecyclerView()
        setUpActionListeners()

        mPresenter.onUiReady(this, this)
        if (mGroupId.isEmpty()) {
            mPresenter.getMessages(mPresenter.getUserId(), mReceiverId)

        } else {
            mPresenter.getGroupMessages(mGroupId.toLong())
        }
    }

    private fun setUpPresenter() {

        mPresenter = getPresenter<ChatDetailPresenterImpl, ChatDetailView>()
    }

    private fun setUpRecyclerView() {
        mChatMessageAdapter = ChatMessageAdapter()
        rvMessage.adapter = mChatMessageAdapter
        rvMessage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun setUpImageRecyclerView() {
        mImageAdapter = ChatImageAdapter()
        rvImage.adapter = mImageAdapter
        rvImage.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setUpActionListeners() {

        btnBack.setOnClickListener {
            onBackPressed()
        }

        btnSendMessage.setOnClickListener {
            val message = edtTypeMessage.text.toString()

            if (message.isNotEmpty() || mImageList.isNotEmpty()) {

                if (mGroupId.isEmpty()) {
                    sendPrivateChatMessage(message = message)
                } else {
                    sendGroupChatMessage(message = message)
                }
            }
            mImageList.clear()
            mImageAdapter.setNewData(mImageList)
            edtTypeMessage.text?.clear()

        }

        btnGallery.setOnClickListener {
            mPresenter.onTapGetImageButton()
        }
    }

    private fun sendPrivateChatMessage(message: String) {
        if (mImageAdapter.itemCount > 0 && message.isEmpty()) {
            for (image in mImageList) {
                val timeStamp = System.currentTimeMillis()
                mPresenter.sendMessage(mPresenter.getUserId(), mReceiverId, timeStamp,
                    MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = ""))
                mPresenter.sendMessage(mReceiverId, mPresenter.getUserId(), timeStamp,
                    MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = ""))
            }
        } else if (message.isNotEmpty() && mImageList.isEmpty()) {
            val timeStamp = System.currentTimeMillis()
            mPresenter.sendMessage(mPresenter.getUserId(), mReceiverId, timeStamp,
                MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = "", message = message, groupName = ""))
            mPresenter.sendMessage(mReceiverId, mPresenter.getUserId(), timeStamp,
                MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = "", message = message, groupName = ""))
        } else {
            val newTimeStamp = System.currentTimeMillis()
            mPresenter.sendMessage(mPresenter.getUserId(), mReceiverId, newTimeStamp,
                MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = newTimeStamp, file = "", message = message, groupName = ""))
            mPresenter.sendMessage(mReceiverId, mPresenter.getUserId(), newTimeStamp,
                MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = newTimeStamp, file = "", message = message, groupName = ""))

            for (image in mImageList) {
                val timeStamp = System.currentTimeMillis()
                mPresenter.sendMessage(mPresenter.getUserId(), mReceiverId, timeStamp,
                    MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = ""))
                mPresenter.sendMessage(mReceiverId, mPresenter.getUserId(), timeStamp,
                    MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = ""))
            }
        }
    }

    private fun sendGroupChatMessage(message: String) {
        if (mImageAdapter.itemCount > 0 && message.isEmpty()) {
            for (image in mImageList) {
                val timeStamp = System.currentTimeMillis()
                mPresenter.sendGroupMessage(mGroupId.toLong(),timeStamp,
                    MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = mGroupName))
            }
        } else if (message.isNotEmpty() && mImageList.isEmpty())  {
            val timeStamp = System.currentTimeMillis()
            mPresenter.sendGroupMessage(mGroupId.toLong(),timeStamp,
                MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = "", message = message, groupName = mGroupName))
        } else {
            val newTimeStamp = System.currentTimeMillis()
            mPresenter.sendGroupMessage(mGroupId.toLong(),newTimeStamp,
                MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = newTimeStamp, file = "", message = message, groupName = mGroupName))

            for (image in mImageList) {
                val timeStamp = System.currentTimeMillis()
                mPresenter.sendGroupMessage(mGroupId.toLong(),timeStamp,
                    MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = mGroupName))
            }
        }
    }

    override fun showUsers(userList: List<UserVO>) {
        for (user in userList) {
            if (mReceiverId == user.userId) {
                tvChatProfile.text = user.userName

                Glide.with(this)
                    .load(user.profilePicture)
                    .into(ivChatProfile)
            }

            if (mPresenter.getUserId() == user.userId) {
                mUserName = user.userName
                mUserProfileImage = user.profilePicture
            }
        }
    }

    override fun showMessages(messageList: List<MessageVO>) {
        mChatMessageAdapter.setNewData(mPresenter.getUserId(), messageList)
        rvMessage.scrollToPosition(messageList.size - 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && (requestCode == IMAGE_REQUEST_CODE || requestCode == IMAGE_CAPTURE_CODE)) {

            val filePath = data?.data
            if (requestCode == IMAGE_CAPTURE_CODE) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                mPresenter.uploadAndSendImage(imageBitmap)
                return
            }
            try {
                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(this.contentResolver, filePath)

                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        mPresenter.uploadAndSendImage(bitmapImage)

                    }else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, filePath
                        )
                        mPresenter.uploadAndSendImage(bitmapImage)

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

    override fun getImageUrlForFile(file: String) {
        mImageList.add(file)
        mImageAdapter.setNewData(mImageList.toList())
    }

    override fun showGroupMessages(messageList: List<MessageVO>) {
        mChatMessageAdapter.setNewData(mPresenter.getUserId(), messageList)
        rvMessage.scrollToPosition(messageList.size - 1)
    }

    override fun getGroups(groupList: List<GroupVO>) {
        for (group in groupList) {
            if (mGroupId == group.id.toString()) {
                tvChatProfile.text = group.name
                mGroupName = group.name

                Glide.with(applicationContext)
                    .load(group.imageUrl)
                    .placeholder(R.drawable.imag1)
                    .into(ivChatProfile)
            }
        }
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}