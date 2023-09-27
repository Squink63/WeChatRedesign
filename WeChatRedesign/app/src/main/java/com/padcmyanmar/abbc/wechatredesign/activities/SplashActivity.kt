package com.padcmyanmar.abbc.wechatredesign.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.SplashPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.SplashPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.SplashView
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity(), SplashView {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SplashActivity::class.java)
        }
    }

    private lateinit var mPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setUpPresenter()
        setUpListeners()

    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<SplashPresenterImpl, SplashView>()
    }

    private fun setUpListeners() {

        btnSignUp.setOnClickListener {
            mPresenter.onTapSignUpButton()
        }

        btnLoginSplash.setOnClickListener {
            mPresenter.onTapLoginButton()
        }
    }

    override fun navigateToLoginScreen() {
        startActivity(LogInActivity.newIntent(this))
    }

    override fun navigateToGetOtpScreen() {
        startActivity(GetOtpActivity.newIntent(this))
    }



}