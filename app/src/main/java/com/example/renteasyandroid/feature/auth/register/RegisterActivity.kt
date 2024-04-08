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
import com.example.renteasyandroid.utils.ProgressDialog
import com.example.renteasyandroid.utils.Status
import com.example.renteasyandroid.utils.showToast
import www.sanju.motiontoast.MotionToastStyle

class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    //initializing the viewmodel (RegisterViewModel)
    private val viewModel: RegisterViewModel by viewModels {
        RegisterViewModel.provideFactory(this)
    }

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
                viewModel.createUser(firstName, lastName, email, phone, password)
            }
        }
    }


//    Validation done to check if the required fields are empty, email is in valid format
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
        observeRegisterResponse()
    }


    //    observes Status with Status types Loading, Complete and Error
    //    Loading : to show the loading
    //    Complete : Called when success
    //    Error : called when there is an error
    private fun observeRegisterResponse() {
        viewModel.registerResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    showProgress()
                }

                Status.COMPLETE -> {
                    hideProgress()
                    showToast("Success", "User created Successfully!", MotionToastStyle.SUCCESS)
                    onBackPressedDispatcher.onBackPressed()
                }

                Status.ERROR -> {
                    hideProgress()
                    showToast(
                        "Error",
                        "Error occurred while creating user!",
                        MotionToastStyle.ERROR
                    )
                }
            }
        }
    }

    //function to show progress dialog
    private fun showProgress() {
        dialog = ProgressDialog.progressDialog(this)
        dialog.show()
    }

    //function to hide or dismiss progress dialog if dialog is showing
    private fun hideProgress() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}