package com.padcmyanmar.abbc.wechatredesign.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.padcmyanmar.abbc.wechatredesign.R
import com.padcmyanmar.abbc.wechatredesign.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpFragment(MomentFragment())
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        bottomNavigation.setOnNavigationItemSelectedListener  { item: MenuItem ->
            when(item.itemId) {

                R.id.moment -> {
                    setUpFragment(MomentFragment())
                }

                R.id.chat -> {
                    setUpFragment(ChatFragment())
                }

                R.id.contact -> {
                    setUpFragment(ContactFragment())
                }

                R.id.me -> {
                    setUpFragment(ProfileFragment())
                }

                R.id.setting -> {
                    setUpFragment(SettingFragment())
                }
            }
            true
        }
    }

    private fun setUpFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, fragment)
            .commit()
    }
}