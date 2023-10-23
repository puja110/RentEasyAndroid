package com.example.renteasyandroid.feature.main.landing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityMainBinding
import com.example.renteasyandroid.feature.main.landing.home.HomeFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity.startActivity(intent)
            activity.finish()
        }
    }
    override fun layout() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaultFragment(savedInstanceState)
    }

    override fun initObservers() {
    }

    private fun setDefaultFragment(savedInstanceState: Bundle?) {
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_container,
                    HomeFragment.getInstance()
                ).commit()
        }
    }
}