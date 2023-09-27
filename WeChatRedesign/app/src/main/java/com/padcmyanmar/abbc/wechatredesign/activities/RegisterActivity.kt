package com.padcmyanmar.abbc.wechatredesign.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_EMAIL
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat.startActivity
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.RegisterPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.RegisterPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.RegisterView
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_profile_edit.*
import java.io.IOException
import java.util.Calendar

class RegisterActivity : BaseActivity(), RegisterView {

    private lateinit var mPresenter: RegisterPresenter

    private var bitmap: Bitmap? = null
    private var day = ""
    private var month = ""
    private var year = ""
    private var gender = ""
    private val IMAGE_REQUEST_CODE = 6666

    companion object{
        private const val EXTRA_PHONE_NUMBER = "PhoneNumber"
        private const val EXTRA_EMAIL = "EmailAddress"
        fun newIntent(context: Context, phoneNumber: String, email: String): Intent {
            val intent = Intent(context, RegisterActivity::class.java)
            intent.putExtra(EXTRA_PHONE_NUMBER, phoneNumber)
            intent.putExtra(EXTRA_EMAIL, email)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        setUpPresenter()
        setUpActionListeners()
        setUpBirthDate()
        getGender()
        
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<RegisterPresenterImpl, RegisterView>()
    }

    private fun setUpActionListeners() {
        btnBack.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        ivChooseProfilePicture.setOnClickListener {
            mPresenter.onTapAddProfilePicture()
        }

        btnSignUp.setOnClickListener {
            bitmap?.let {
                mPresenter.onTapSignUpButton(it, getUserInfo())
            }
        }

    }

    private fun getUserInfo(): UserVO {

        val userName = edtName.text.toString()
        val email = intent?.getStringExtra(EXTRA_EMAIL) ?: ""
        val phoneNumber = intent?.getStringExtra(EXTRA_PHONE_NUMBER) ?: ""
        val password = edtPassword.text.toString()

        return UserVO(
            userName = userName,
            email = email,
            phoneNumber = phoneNumber,
            password = password,
            birthDate = "$year-$month-$day",
            gender = gender

        )
    }

    private fun getGender() {
//        if (rgGender!=null) {
//
//        }
        radioBtnMale.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                gender = "Male"
            }
        }

        radioBtnFemale.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                gender = "Female"
            }
        }

        radioBtnOther.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                gender = "Other"
            }
        }
        
    }

    private fun setUpBirthDate() {

        setYearSpinner()

        spDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0){
                    day = spDay.selectedItemPosition.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                month = spMonth.selectedItemPosition.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                year = spYear.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    private fun setYearSpinner() {

        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val years = arrayListOf("Year")
        for (year in 1900..currentYear) {
            years.add(year.toString())
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spYear.adapter = adapter
    }
    
    

    override fun navigateToBackScreen() {
        finish()
    }

    override fun navigateToLoginScreen() {
        startActivity(LogInActivity.newIntent(this))
        finish()
    }

    override fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action= Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Image Selected"), IMAGE_REQUEST_CODE)
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
                        bitmap = bitmapImage
                        ivChooseProfilePicture.setImageBitmap(bitmap)
                    }else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, filePath
                        )
                        bitmap = bitmapImage
                        ivChooseProfilePicture.setImageBitmap(bitmap)
                    }
                }
            }catch (e : IOException) {
                e.printStackTrace()
            }
        }
    }







}