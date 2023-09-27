package com.padcmyanmar.abbc.wechatredesign.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.adapters.AddMomentImageAdapter
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.CreateMomentPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.CreateMomentPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.CreateMomentView
import kotlinx.android.synthetic.main.activity_create_moment.*
import kotlinx.android.synthetic.main.activity_register.*
import java.io.IOException

class CreateMomentActivity : BaseActivity(), CreateMomentView {

    private lateinit var mAdapter: AddMomentImageAdapter

    private lateinit var mPresenter: CreateMomentPresenter

    private var userName: String = ""
    private var userProfileImage: String = ""
    private var userId:String = ""
    private val IMAGE_REQUEST_CODE = 6666

    companion object{

        fun newIntent(context: Context): Intent {
            return Intent(context, CreateMomentActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_moment)

        setUpPresenter()

        setUpRecyclerView()
        setUpActionListeners()

        mPresenter.onUiReady(this,this)
    }

    private fun setUpPresenter() {

        mPresenter = getPresenter<CreateMomentPresenterImpl, CreateMomentView>()
    }

    private fun setUpActionListeners() {

        btnCancel.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        btnCreate.setOnClickListener {
            mPresenter.onTapCreateButton(getMoment())
            finish()
        }
    }

    private fun getMoment(): MomentVO {
        val caption = edtCreateMomentText.text.toString()
        return MomentVO(
            System.currentTimeMillis().toString(),
            userId,
            userName,
            userProfileImage,
            caption,
            mPresenter.getMomentImages().dropLast(1)
        )
    }

    private fun setUpRecyclerView() {

        mAdapter = AddMomentImageAdapter(mPresenter)
        rvAddMomentImage.adapter = mAdapter
        rvAddMomentImage.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun navigateToBackScreen() {
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {

            val filePath = data?.data
            mAdapter.setNewData(filePath.toString())
            try {
                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(this.contentResolver, filePath)

                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        mPresenter.createMomentImages(bitmapImage)
                    }else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, filePath
                        )
                        mPresenter.createMomentImages(bitmapImage)
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

    override fun showUserInformation(userList: List<UserVO>) {
        for (user in userList) {
            if (mPresenter.getUserId() == user.userId) {

                userId = user.userId
                userName = user.userName
                userProfileImage = user.profilePicture

                tvUploadUserName.text = user.userName

                Glide.with(this)
                    .load(user.profilePicture)
                    .into(ivUploadUserImage)
            }
        }
    }
}