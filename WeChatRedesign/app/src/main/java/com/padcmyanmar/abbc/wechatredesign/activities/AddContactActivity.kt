package com.padcmyanmar.abbc.wechatredesign.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.data.vos.UserVO
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.AddContactPresenter
import com.padcmyanmar.abbc.wechatredesign.mvp.presenters.impls.AddContactPresenterImpl
import com.padcmyanmar.abbc.wechatredesign.mvp.views.AddContactView
import kotlinx.android.synthetic.main.activity_add_contact.*

class CaptureActivityPortrait: CaptureActivity()
class AddContactActivity : BaseActivity(), AddContactView {

    private lateinit var mPresenter: AddContactPresenter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AddContactActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        setUpPresenter()

        setUpActionListener()
    }

    private fun setUpPresenter() {
        mPresenter = getPresenter<AddContactPresenterImpl, AddContactView>()
    }

    private fun setUpActionListener() {
        btnBckTo.setOnClickListener {
            onBackPressed()
        }

        ivTapScan.setOnClickListener {
            setUpQRCodeScanner()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                mPresenter.getUsers(result.contents)
                Toast.makeText(this, "Scanning Completed: " + result.contents, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun setUpQRCodeScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Scan QR Code")
        integrator.setCameraId(0) // Use a specific camera of the device

        integrator.setOrientationLocked(true)
        integrator.setBeepEnabled(true)
        integrator.captureActivity = CaptureActivityPortrait::class.java
        integrator.initiateScan()
    }

    override fun getUsers(userList: List<UserVO>, exporterUserId: String) {
        outer@ for (user in userList) {
            if (mPresenter.getScannerUserId() == user.userId) {
                for (exportUser in userList) {
                    if (exporterUserId == exportUser.userId) {
                        mPresenter.createContact(
                            mPresenter.getScannerUserId(),
                            exporterUserId,
                            exportUser
                        )
                        break@outer
                    }
                }
            }
        }

        outer@ for (user in userList) {
            if (exporterUserId == user.userId) {
                for (scannerUser in userList) {
                    if (mPresenter.getScannerUserId() == scannerUser.userId) {
                        mPresenter.createContact(
                            exporterUserId,
                            mPresenter.getScannerUserId(),
                            scannerUser
                        )
                        break@outer
                    }
                }
            }
        }
    }
}