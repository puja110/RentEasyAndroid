package com.example.renteasyandroid.feature.auth.forgotpassword

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding>() {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, ForgotPasswordActivity::class.java)
            activity.startActivity(intent)
        }
    }
    override fun layout(): Int = R.layout.activity_forgot_password

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnConfirm.setOnClickListener {
            VerifyEmailActivity.start(this)
        }
    }

    override fun initObservers() {
    }
}