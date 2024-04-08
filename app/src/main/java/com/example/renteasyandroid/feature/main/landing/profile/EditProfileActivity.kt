package com.example.renteasyandroid.feature.main.landing.profile

import android.app.Activity
import android.app.Dialog
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
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.feature.main.landing.add.PropertyImageAdapter
import com.example.renteasyandroid.utils.SharedPreferenceManager
import com.example.renteasyandroid.utils.showToast
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import www.sanju.motiontoast.MotionToastStyle

class EditProfileActivity : AppCompatActivity() {

    private var mCameraUri = mutableListOf<Uri>()

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                mCameraUri.add(uri)
                Log.d("mCameraUri: ", mCameraUri.toString())
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraintLayout)
        val btnBack = findViewById<ImageView>(R.id.imageArrowLeft)
        val etFirstName = findViewById<EditText>(R.id.etFistName)
        val etLastName = findViewById<EditText>(R.id.etLastName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val btnProfileEdit = findViewById<AppCompatButton>(R.id.btnProfileEdit)
        val addPropertyImage = findViewById<ImageView>(R.id.iv_add_property_image)

        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        addPropertyImage.setOnClickListener {
            if (mCameraUri.size < 5) {
                cameraLauncher.launch(
                    ImagePicker.with(this)
                        .crop()
                        .cameraOnly()
                        .maxResultSize(1080, 1920, true)
                        .createIntent()
                )
            } else {
                showToast("Warning", "You can only add 5 images", MotionToastStyle.WARNING)
            }
        }

        btnProfileEdit.setOnClickListener {
            val firstname = etFirstName.text.toString().trim()
            val lastname = etLastName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            if(mCameraUri.isNotEmpty()) {
                Glide.with(this)
                    .load(mCameraUri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo).into(addPropertyImage)
            }

            if (firstname.isEmpty()) {
                val snackbar = Snackbar
                    .make(constraintLayout, "Fistname field cannot be empty!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else if (lastname.isEmpty()) {
            val snackbar = Snackbar
                .make(constraintLayout, "Lastname field cannot be empty!", Snackbar.LENGTH_LONG)
            snackbar.show()
            }  else if (email.isEmpty()) {
                val snackbar = Snackbar
                    .make(constraintLayout, "Email field cannot be empty!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else if (!email.contains("@")) {
                val snackbar = Snackbar
                    .make(constraintLayout, "Enter valid email!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else if (phone.isEmpty()) {
                val snackbar = Snackbar
                    .make(constraintLayout, "Phone field cannot be empty!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}