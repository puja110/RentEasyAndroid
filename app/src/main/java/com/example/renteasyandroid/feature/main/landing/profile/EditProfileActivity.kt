package com.example.renteasyandroid.feature.main.landing.profile

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityEditProfileBinding
import com.example.renteasyandroid.feature.main.data.model.UserDetail
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.utils.Status
import com.example.renteasyandroid.utils.showToast
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.storage
import www.sanju.motiontoast.MotionToastStyle

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding>() {

    private var addPropertyImage: ImageView? = null

    private var btnBack: ImageView? = null
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPhone: EditText? = null
    private var btnProfileEdit: AppCompatButton? = null

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(this)
    }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                Glide.with(this)
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logo).into(addPropertyImage!!)
                Log.d("mCameraUri: ", uri.toString())
                val storage = Firebase.storage("gs://renteasy-7a973.appspot.com")
                val childPath =
                    "profileImages/${FirebaseAuth.getInstance().currentUser?.uid ?: "atLHJvnU33cWRhr210smoxvnBEg2"}.jpg"
                storage.reference.child(childPath).putFile(uri).addOnSuccessListener {
                    Toast.makeText(this, "Successfully Updated Avatar", Toast.LENGTH_SHORT)
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to Upload Avatar", Toast.LENGTH_SHORT)
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

        btnBack = findViewById<ImageView>(R.id.imageArrowLeft)
        etFirstName = findViewById<EditText>(R.id.etFistName)
        etLastName = findViewById<EditText>(R.id.etLastName)
        etEmail = findViewById<EditText>(R.id.etEmail)
        etPhone = findViewById<EditText>(R.id.etPhone)
        btnProfileEdit = findViewById<AppCompatButton>(R.id.btnProfileEdit)
        addPropertyImage = findViewById(R.id.iv_add_property_image)
        val layout = findViewById<ConstraintLayout>(R.id.constraintLayout)

        btnBack?.setOnClickListener {
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

        btnProfileEdit?.setOnClickListener {
            val firstname = etFirstName?.text.toString().trim()
            val lastname = etLastName?.text.toString().trim()
            val email = etEmail?.text.toString().trim()
            val phone = etPhone?.text.toString().trim()


            if (firstname.isEmpty()) {
                val snackbar = Snackbar
                    .make(layout, "Fistname field cannot be empty!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else if (lastname.isEmpty()) {
                val snackbar = Snackbar
                    .make(layout, "Lastname field cannot be empty!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else if (email.isEmpty()) {
                val snackbar = Snackbar
                    .make(layout, "Email field cannot be empty!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else if (!email.contains("@")) {
                val snackbar = Snackbar
                    .make(layout, "Enter valid email!", Snackbar.LENGTH_LONG)
                snackbar.show()
            } else if (phone.isEmpty()) {
                val snackbar = Snackbar
                    .make(layout, "Phone field cannot be empty!", Snackbar.LENGTH_LONG)
                snackbar.show()
            }

            val user = UserDetail(email, firstname, lastname, phone)
            viewModel.setUserDetail(user)

        }

        viewModel.getUserDetail()
    }

    override fun initObservers() {
        observeUserDetail()
        observeSetUserDetail()
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
                        etEmail?.setText(userDetail.email)
                        etFirstName?.setText(userDetail.firstName)
                        etLastName?.setText(userDetail.lastName)
                        etPhone?.setText(userDetail.phoneNumber)
                    }
                }

                Status.ERROR -> {
                    // Optionally handle error state, e.g., show an error message
                }
            }
        }
    }

    private fun observeSetUserDetail() {
        viewModel.setUserDetailStatus.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    // Show loading indicator
                }

                Status.COMPLETE -> {
                    showToast("Success", "Successfully Updated User", MotionToastStyle.INFO)
                    // Update UI to reflect the favorite has been added
                    viewModel.getUserDetail()
                    onBackPressedDispatcher.onBackPressed()
                }

                Status.ERROR -> {
                    showToast("Error", "Failed to Updated User", MotionToastStyle.ERROR)
                    // Optionally, update UI to reflect the failure
                }
            }
        }
    }


}