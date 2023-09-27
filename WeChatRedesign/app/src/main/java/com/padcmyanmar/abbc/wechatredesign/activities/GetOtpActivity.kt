package com.padcmyanmar.abbc.wechatredesign.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.GetOtpPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.GetOtpPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.GetOtpView
import kotlinx.android.synthetic.main.activity_get_otp.*

class GetOtpActivity : BaseActivity(), GetOtpView {

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, GetOtpActivity::class.java)
        }
    }

    private lateinit var mPresenter: GetOtpPresenter
    private var mOtp = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_otp)

        setUpPresenter()
        setUpActionListeners()

        mPresenter.onUiReady(this, this)

    }

    private fun setUpPresenter() {

        mPresenter = getPresenter<GetOtpPresenterImpl, GetOtpView>()
    }

    private fun setUpActionListeners() {

        btnVerify.setOnClickListener {
            mPresenter.onTapVerifyButton()
        }

        btnBack.setOnClickListener {
            mPresenter.onTapBackButton()
        }
    }

    override fun navigateToBackScreen() {
        finish()
    }

    override fun navigateToRegisterScreen() {
        val phoneNumber = edtPhoneNumber.text.toString()
        val email = edtEmail.text.toString()

        val edtOne = edtOtp1.text.toString()
        val edtTwo = edtOtp2.text.toString()
        val edtThree = edtOtp3.text.toString()
        val edtFour = edtOtp4.text.toString()
        val combinedValue = "$edtOne$edtTwo$edtThree$edtFour"

        if (combinedValue == mOtp) {
            startActivity(RegisterActivity.newIntent(this, phoneNumber, email))
        } else{
            Snackbar.make(window.decorView, "Wrong Otp,Try Again", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun showOtp(otp: String) {
        mOtp = otp
        Toast.makeText(this,otp, Toast.LENGTH_LONG).show()
    }
}