package com.example.renteasyandroid.feature.auth.forgotpassword

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityVerifyEmailBinding
import com.example.renteasyandroid.feature.auth.login.LoginActivity

class VerifyEmailActivity : BaseActivity<ActivityVerifyEmailBinding>() {

    private val viewModel: ForgotPasswordViewModel by viewModels()

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, VerifyEmailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun layout() = R.layout.activity_verify_email

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvBackToLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnVerify.setOnClickListener {
            Toast.makeText(this, R.string.verify_email_success_msg, Toast.LENGTH_SHORT).show()
        }
        binding.tvBackToLogin.setOnClickListener {
            LoginActivity.start(this)
        }
    }

    override fun initObservers() {
    }
}