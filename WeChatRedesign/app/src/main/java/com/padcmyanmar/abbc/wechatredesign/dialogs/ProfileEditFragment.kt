package com.padcmyanmar.abbc.wechatredesign.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.padcmyanmar.abbc.wechatredesign.R
import kotlinx.android.synthetic.main.fragment_profile_edit.view.*

class ProfileEditFragment(context: Context): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("DEPRECATION")
        this.ownerActivity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val windowParams = window?.attributes
        windowParams?.width = WindowManager.LayoutParams.MATCH_PARENT
        windowParams?.height = WindowManager.LayoutParams.WRAP_CONTENT
        window?.attributes = windowParams
    }
}