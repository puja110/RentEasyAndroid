package com.example.renteasyandroid.feature.main.landing.profile

import android.app.Activity
import android.app.Dialog
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Layout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityEditProfileBinding
import com.example.renteasyandroid.databinding.ActivityLoginBinding
import com.example.renteasyandroid.feature.auth.login.LoginViewModel
import com.example.renteasyandroid.feature.main.data.model.UserDetail
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.feature.main.landing.add.PropertyImageAdapter
import com.example.renteasyandroid.remote.ApiService
import com.example.renteasyandroid.utils.SharedPreferenceManager
import com.example.renteasyandroid.utils.Status
import com.example.renteasyandroid.utils.showToast
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import www.sanju.motiontoast.MotionToastStyle

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>() {

    private var addPropertyImage: ImageView? = null

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(this)
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!

                if(addPropertyImage != null) {
                    Glide.with(this)
                        .load(uri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.logo).into(addPropertyImage!!)
                }
            } else {
                parseError(it)
            }
        }

    private fun parseError(activityResult: ActivityResult) {
        if (activityResult.resultCode == ImagePicker.RESULT_ERROR) {
            showToast("Error", ImagePicker.getError(activityResult.data), MotionToastStyle.ERROR)
        } else {
            showToast("Error", "Task Cancelled!", MotionToastStyle.ERROR)
        }
    }

    override fun layout() = R.layout.activity_edit_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        viewModel.getUserDetail()

        val btnBack = findViewById<ImageView>(R.id.imageArrowLeft)
        val etFirstName = findViewById<EditText>(R.id.etFistName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnProfileEdit = findViewById<AppCompatButton>(R.id.btnProfileEdit)
        addPropertyImage = findViewById(R.id.iv_add_property_image)


        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        addPropertyImage?.setOnClickListener {
            cameraLauncher.launch(
                ImagePicker.with(this)
                    .crop()
                    .cameraOnly()
                    .maxResultSize(1080, 1920, true)
                    .createIntent()
            )
        }

        btnProfileEdit.setOnClickListener {
            val firstname = etFirstName.text.toString().trim()
            val lastname = etLastName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()


//            if (firstname.isEmpty()) {
//                val snackbar = Snackbar
//                    .make(constraintLayout, "Fistname field cannot be empty!", Snackbar.LENGTH_LONG)
//                snackbar.show()
//            } else if (lastname.isEmpty()) {
//            val snackbar = Snackbar
//                .make(constraintLayout, "Lastname field cannot be empty!", Snackbar.LENGTH_LONG)
//            snackbar.show()
//            }  else if (email.isEmpty()) {
//                val snackbar = Snackbar
//                    .make(constraintLayout, "Email field cannot be empty!", Snackbar.LENGTH_LONG)
//                snackbar.show()
//            } else if (!email.contains("@")) {
//                val snackbar = Snackbar
//                    .make(constraintLayout, "Enter valid email!", Snackbar.LENGTH_LONG)
//                snackbar.show()
//            } else if (phone.isEmpty()) {
//                val snackbar = Snackbar
//                    .make(constraintLayout, "Phone field cannot be empty!", Snackbar.LENGTH_LONG)
//                snackbar.show()
//            } else {
//                onBackPressedDispatcher.onBackPressed()
//            }



        }
    }

    override fun initObservers() {
        observeUserDetail()
    }

    private fun observeUserDetail() {
        viewModel.userDetailResponse.observe(this) { response -> // Use viewLifecycleOwner in Fragments
            when (response.status) {
                Status.LOADING -> {
                    // Optionally handle loading state, e.g., show a progress indicator
                }

                Status.COMPLETE -> {
                    response.data?.let { userDetail ->
                        // Set text views with user details
                        binding.etEmail.setText(userDetail.email)
                        binding.etFistName.setText(userDetail.firstName)
                        binding.etLastName.setText(userDetail.lastName)
                        binding.etPhone.setText(userDetail.phoneNumber)
                    }
                }

                Status.ERROR -> {
                    // Optionally handle error state, e.g., show an error message
                }
            }
        }
    }



}