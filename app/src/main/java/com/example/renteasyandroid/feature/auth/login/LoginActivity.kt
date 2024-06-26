package com.example.renteasyandroid.feature.auth.login

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityLoginBinding
import com.example.renteasyandroid.feature.auth.forgotpassword.ForgotPasswordActivity
import com.example.renteasyandroid.feature.auth.register.RegisterActivity
import com.example.renteasyandroid.feature.main.landing.MainActivity
import com.example.renteasyandroid.utils.PreferenceHelper
import com.example.renteasyandroid.utils.PreferenceHelper.setUsername
import com.example.renteasyandroid.utils.Permissons.checkFineLocation
import com.example.renteasyandroid.utils.Permissons.requestFineLocation
import com.example.renteasyandroid.utils.ProgressDialog
import com.example.renteasyandroid.utils.SharedPreferenceManager
import com.example.renteasyandroid.utils.Status
import com.example.renteasyandroid.utils.showToast
import com.google.firebase.auth.FirebaseAuth
import www.sanju.motiontoast.MotionToastStyle

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModel.provideFactory(this)
    }

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
        val prefs = PreferenceHelper.customPreference(this)

        if (!this.checkFineLocation()) {
            this.requestFineLocation(123)
        }

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
                if (binding.cbRemember.isChecked) {
                    preference.email = email
                    preference.password = password
                }
                prefs.setUsername = email

                userSignIn()
            }
        }
        binding.btnCreateAccount.setOnClickListener {
            RegisterActivity.start(this)
        }
        binding.tvForgotPassword.setOnClickListener {
            ForgotPasswordActivity.start(this)
        }
    }

    private fun userSignIn() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("Login success: ", "signInWithEmail:success")
                    MainActivity.start(this)
                } else {
                    Log.w("Login failed: ", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed, check your login credential.", Toast.LENGTH_SHORT,).show()
                }
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
        observeLoginResponse()
    }

    private fun observeLoginResponse() {
        viewModel.loginResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    showProgress()
                }

                Status.COMPLETE -> {
                    hideProgress()
                    if (response.data?.isNotEmpty() == true) {
                        preference.uuid = response.data
                        showToast("Success", "Login Successful!", MotionToastStyle.SUCCESS)
                        MainActivity.start(this)
                    } else {
                        showToast(
                            "Error",
                            response.data.toString(),
                            MotionToastStyle.ERROR
                        )
                    }

                }

                Status.ERROR -> {
                    hideProgress()
                    showToast(
                        "Error",
                        response.error.toString(),
                        MotionToastStyle.ERROR
                    )
                }
            }
        }
    }

    private fun showProgress() {
        dialog = ProgressDialog.progressDialog(this)
        dialog.show()
    }

    private fun hideProgress() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}