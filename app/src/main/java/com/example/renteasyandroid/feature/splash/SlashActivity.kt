package com.example.renteasyandroid.feature.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivitySplashBinding
import com.example.renteasyandroid.feature.auth.login.LoginActivity

class SlashActivity : BaseActivity<ActivitySplashBinding>() {
    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, SlashActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun layout() = R.layout.activity_splash

    /**
     * Creates a splash screen and
     * delays 2 seconds before it navigates back to login screen
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //navigate to login screen after 2 seconds delay
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }


    override fun initObservers() {
    }
}