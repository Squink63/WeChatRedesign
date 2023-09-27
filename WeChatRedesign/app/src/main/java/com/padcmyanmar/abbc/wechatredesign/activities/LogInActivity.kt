package com.padcmyanmar.abbc.wechatredesign.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.LoginPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.LoginPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*

class LogInActivity : BaseActivity(), LoginView {

    private lateinit var mPresenter: LoginPresenter

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, LogInActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpPresenter()
        setUpActionListeners()

    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<LoginPresenterImpl, LoginView>()
    }

    private fun setUpActionListeners() {

        btnBack.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        btnLogin.setOnClickListener {
            mPresenter.onTapLoginButton(edtEmailLogin.text.toString(), edtPassword.text.toString(), edtPhone.text.toString())
        }
    }

    override fun navigateToMainScreen() {
        startActivity(MainActivity.newIntent(this))
    }

    override fun navigateToBackScreen() {
        finish()
    }
}