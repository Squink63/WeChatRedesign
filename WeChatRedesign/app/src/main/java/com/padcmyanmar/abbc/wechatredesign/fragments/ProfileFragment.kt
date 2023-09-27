package com.padcmyanmar.abbc.wechatredesign.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.play.core.integrity.p
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.MomentVO
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.dialogs.ProfileEditFragment
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.ProfilePresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.ProfilePresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.ProfileView
import com.padcmyanmar.abbc.wechatredesign.viewpods.MomentViewPod
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import kotlinx.android.synthetic.main.layout_qrcode_overlay.*
import java.io.IOException
import java.util.*


class ProfileFragment : Fragment(), ProfileView {

    private lateinit var mPresenter: ProfilePresenter
    private lateinit var mViewPod: MomentViewPod

    private val IMAGE_REQUEST_CODE = 6666
    private var bitmap: Bitmap? = null
    private var mUser: UserVO? = null
    private var mMomentList:ArrayList<MomentVO> = arrayListOf()
    private var email: String = ""
    private var password: String = ""
    private var qrCode: String = ""
    private var profilePicture: String = ""
    private var day: String = ""
    private var month: String = ""
    private var year: String = ""
    private var gender: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()

        setUpViewPod()
        setUpActionListeners()

        mPresenter.onUiReady(requireContext(), this)

    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[ProfilePresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpActionListeners() {

        btnEditProfile.setOnClickListener {
            mPresenter.onTapEditProfileButton()
        }

        btnAddProfileImage.setOnClickListener {
            mPresenter.onTapProfilePicture()
        }

        ivQRCode.setOnClickListener {
            mPresenter.onTapQRCode()
        }
    }

    private fun setUpQRCodeOverlay() {
        val alertDialog = Dialog(requireContext())
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertDialog.setContentView(R.layout.layout_qrcode_overlay)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.ivQRCodeBig.setImageBitmap(textToImageEncode(qrCode))
        alertDialog.show()

    }

    private fun setUpViewPod() {

        mViewPod = vpProfileBookmarkedMoment as MomentViewPod
        mViewPod.setUpMomentViewPod(mPresenter)
    }

    override fun showUserInfo(userList: List<UserVO>) {
        for (user in userList)
            if (mPresenter.getUserId() == user.userId) {
                mUser = user
                tvProfileName.text = user.userName
                tvPhoneNumber.text = user.phoneNumber
                tvBirthDate.text = user.birthDate
                tvGenderEdit.text = user.gender

                email = user.email
                password = user.password
                qrCode = user.qrCode
                profilePicture = user.profilePicture

                Glide.with(requireActivity())
                    .load(user.profilePicture)
                    .into(ivProfileImage)

                ivQRCode.setImageBitmap(textToImageEncode(qrCode))
            }
    }

    override fun showEditProfileDialog() {
        val dialog = ProfileEditFragment(requireActivity())
        dialog.setContentView(R.layout.fragment_profile_edit)
        dialog.setCancelable(true)

        dialog.edtChangeName.setText(tvProfileName.text.toString())
        dialog.edtChangePhone.setText(tvPhoneNumber.text.toString())

        setUpSpinners(dialog)
        getGender(dialog)

        dialog.btnSave.setOnClickListener {
            val userName = dialog.edtChangeName.text.toString()
            val phoneNumber = dialog.edtChangePhone.text.toString()
            val birthDate = "$year-$month-$day"
            val user = UserVO( email, password, userName, phoneNumber,mPresenter.getUserId(), profilePicture, qrCode, birthDate, gender)
            mPresenter.updateUserInfo(user)
            dialog.dismiss()
        }

        dialog.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Upload Image"), IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION")
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {

            val filePath = data?.data

            try {
                filePath?.let {
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source: ImageDecoder.Source =
                            ImageDecoder.createSource(requireActivity().contentResolver, filePath)

                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        bitmap = bitmapImage
                        bitmap?.let { image ->
                            mUser?.let { user ->
                                mPresenter.uploadAndUpdateProfilePicture(image, user)
                            }
                        }
                    }else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            context?.applicationContext?.contentResolver, filePath
                        )
                        bitmap = bitmapImage
                        bitmap?.let { image ->
                            mUser?.let { user ->
                                mPresenter.uploadAndUpdateProfilePicture(image, user)
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun showQRCode() {
        setUpQRCodeOverlay()
    }

    override fun showMoments(momentList: List<MomentVO>) {
        for (moment in momentList) {
            if (moment.isBookmarked) {
                mMomentList.add(moment)
            }
        }
        mViewPod.setNewData(mMomentList)
    }

    override fun getMomentIsBookmarked(id: String, bookmarked: Boolean) {
        for(moment in mMomentList) {
            if(id == moment.id) {
                if(!bookmarked) {
                    moment.isBookmarked = false
                    mPresenter.createMoment(moment)
                    break
                }
            }
        }
        mViewPod.setNewData(mMomentList)
    }

    override fun showError(error: String) {
        Toast.makeText(requireActivity(),error, Toast.LENGTH_SHORT).show()
    }

    @Throws(WriterException::class)
    fun textToImageEncode(Value: String?): Bitmap? {
        val bitMatrix: BitMatrix
        try {
            bitMatrix = MultiFormatWriter().encode(
                Value,
                BarcodeFormat.QR_CODE,
                300, 300, null
            )
        } catch (IllegalArgumentException: IllegalArgumentException) {
            return null
        }
        val bitMatrixWidth = bitMatrix.width
        val bitMatrixHeight = bitMatrix.height
        val pixels = IntArray(bitMatrixWidth * bitMatrixHeight)
        for (y in 0 until bitMatrixHeight) {
            val offset = y * bitMatrixWidth
            for (x in 0 until bitMatrixWidth) {
                pixels[offset + x] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444)
        bitmap.setPixels(pixels, 0, 300, 0, 0, bitMatrixWidth, bitMatrixHeight)
        return bitmap
    }

    private fun setUpSpinners(dialog: Dialog) {

        setYearSpinner(dialog)

        dialog.spDayEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0){
                    day = dialog.spDayEdit?.selectedItemPosition.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        dialog.spMonthEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    month = dialog.spMonthEdit?.selectedItemPosition.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        dialog.spYearEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    year = dialog.spYearEdit?.selectedItem.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun setYearSpinner(dialog: Dialog) {

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = arrayListOf("Year")
        for (year in 1900..currentYear) {
            years.add(year.toString())
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialog.spYearEdit.adapter = adapter
    }

    private fun getGender(dialog: Dialog) {
//        if (rgGender!=null) {
//
//        }
        dialog.rbMaleEdit.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                gender = "Male"
            }
        }

        dialog.rbFeMaleEdit.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                gender = "Female"
            }
        }

        dialog.rbOtherEdit.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                gender = "Other"
            }
        }

    }

}