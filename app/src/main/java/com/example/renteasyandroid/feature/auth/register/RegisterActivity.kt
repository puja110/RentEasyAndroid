package com.example.renteasyandroid.feature.auth.register

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityRegisterBinding
import com.example.renteasyandroid.feature.auth.forgotpassword.VerifyEmailActivity
import com.example.renteasyandroid.utils.showToast
import www.sanju.motiontoast.MotionToastStyle

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private val viewModel: RegisterViewModel by viewModels()

    private var firstName = ""
    private var lastName = ""
    private var email = ""
    private var phone = ""
    private var password = ""
    private var confirmPassword = ""

    private lateinit var dialog: Dialog

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, RegisterActivity::class.java)
            activity.startActivity(intent)
        }
    }
    override fun layout() = R.layout.activity_register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tvBackToLogin.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnRegister.setOnClickListener {
            if (isValid()) {
                VerifyEmailActivity.start(this)
            }
        }
    }

    private fun isValid(): Boolean {
        firstName = binding.etFirstName.text.toString()
        lastName = binding.etLastName.text.toString()
        email = binding.etEmail.text.toString()
        phone = binding.etPhone.text.toString()
        password = binding.etPassword.text.toString()
        confirmPassword = binding.etConfirmPassword.text.toString()
        if (firstName.isEmpty()) {
            showToast("Warning", "First Name field cannot be empty!", MotionToastStyle.WARNING)
            return false
        }

        if (lastName.isEmpty()) {
            showToast("Warning", "Last Name field cannot be empty!", MotionToastStyle.WARNING)
            return false
        }

        if (email.isEmpty()) {
            showToast("Warning", "Email field cannot be empty!", MotionToastStyle.WARNING)
            return false
        }

        if (phone.isEmpty()) {
            showToast("Warning", "Phone field cannot be empty!", MotionToastStyle.WARNING)
            return false
        }

        if (password.isEmpty()) {
            showToast("Warning", "Password field cannot be empty!", MotionToastStyle.WARNING)
            return false
        }

        if (confirmPassword.isEmpty()) {
            showToast(
                "Warning",
                "Confirm Password field cannot be empty!",
                MotionToastStyle.WARNING
            )
            return false
        }

        if (password != confirmPassword) {
            showToast(
                "Warning",
                "Confirm Password and Password must be same!",
                MotionToastStyle.WARNING
            )
            return false
        }

        return true
    }

    override fun initObservers() {
    }
}