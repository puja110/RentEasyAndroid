package com.example.renteasyandroid.feature.auth.login

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityLoginBinding
import com.example.renteasyandroid.feature.auth.forgotpassword.ForgotPasswordActivity
import com.example.renteasyandroid.feature.auth.register.RegisterActivity
import com.example.renteasyandroid.feature.main.landing.MainActivity
import com.example.renteasyandroid.utils.SharedPreferenceManager
import com.example.renteasyandroid.utils.showToast
import www.sanju.motiontoast.MotionToastStyle

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    private var email = ""
    private var password = ""

    private lateinit var dialog: Dialog
    private lateinit var preference: SharedPreferenceManager

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity.startActivity(intent)
            activity.finish()
        }
    }

    override fun layout() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preference = SharedPreferenceManager(this)
        if (preference.email?.isNotEmpty() == true && preference.password?.isNotEmpty() == true) {
            binding.cbRemember.isChecked = true
            binding.etEmail.setText(preference.email)
            binding.etPassword.setText(preference.password)
        }

        binding.cbRemember.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (!isChecked) {
                preference.email = ""
                preference.password = ""
            }
        }

        binding.btnLogin.setOnClickListener {
            if (isValid()) {
                MainActivity.start(this)
            }
        }
        binding.btnCreateAccount.setOnClickListener {
            RegisterActivity.start(this)
        }
        binding.tvForgotPassword.setOnClickListener {
            ForgotPasswordActivity.start(this)
        }
    }

    private fun isValid(): Boolean {
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()

        if (email.isEmpty()) {
            showToast("Warning", "Email field cannot be empty!", MotionToastStyle.WARNING)
            return false
        }

        if (password.isEmpty()) {
            showToast("Warning", "Password field cannot be empty!", MotionToastStyle.WARNING)
            return false
        }

        return true
    }

    override fun initObservers() {
    }
}