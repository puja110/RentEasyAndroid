package com.example.renteasyandroid.feature.main.landing.add

import android.app.Activity
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseFragment
import com.example.renteasyandroid.databinding.FragmentPostRentBinding
import com.example.renteasyandroid.feature.main.data.model.AddPostRequest
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.utils.ProgressDialog
import com.example.renteasyandroid.utils.SharedPreferenceManager
import com.example.renteasyandroid.utils.Status
import com.example.renteasyandroid.utils.showToast
import com.github.drjacky.imagepicker.ImagePicker
import www.sanju.motiontoast.MotionToastStyle

class PostRentFragment : BaseFragment<FragmentPostRentBinding>() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(requireContext())
    }
    private var mCameraUri = mutableListOf<Uri>()
    private var adapter: PropertyImageAdapter? = null
    private lateinit var preference: SharedPreferenceManager
    private lateinit var dialog: Dialog

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                mCameraUri.add(uri)
                adapter?.updateData(mCameraUri)
                adapter?.notifyItemChanged(mCameraUri.size - 1)
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


    override fun layout(): Int = R.layout.fragment_post_rent


    companion object {
        fun getInstance(): Fragment {
            return PostRentFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preference = SharedPreferenceManager(requireContext())

        adapter = PropertyImageAdapter(mCameraUri, {})
        val layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvPropertyImage.layoutManager = layoutManager
        binding.rvPropertyImage.adapter = adapter

        binding.ivAddPropertyImage.setOnClickListener {
            if (mCameraUri.size < 5) {
                cameraLauncher.launch(
                    ImagePicker.with(requireActivity())
                        .crop()
                        .cameraOnly()
                        .maxResultSize(1080, 1920, true)
                        .createIntent()
                )
            } else {
                showToast("Warning", "You can only add 5 images", MotionToastStyle.WARNING)
            }
        }

        binding.btnLogin.setOnClickListener {
            val title = binding.etTitle.text.toString().trim()
            val type = binding.etType.text.toString().trim()
            val price = binding.etPrice.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()
            if (title.isEmpty()) {
                showToast("Warning", "Title field cannot be empty!", MotionToastStyle.WARNING)
            } else if (type.isEmpty()) {
                showToast("Warning", "Type field cannot be empty!", MotionToastStyle.WARNING)
            } else if (price.isEmpty()) {
                showToast("Warning", "Price field cannot be empty!", MotionToastStyle.WARNING)
            } else if (address.isEmpty()) {
                showToast("Warning", "Address field cannot be empty!", MotionToastStyle.WARNING)
            } else if (description.isEmpty()) {
                showToast("Warning", "Description field cannot be empty!", MotionToastStyle.WARNING)
            } else {
                val request = AddPostRequest(
                    description = description,
                    isBooked = false,
                    isNegotiable = binding.rbNegotiable.isChecked,
                    latitude = 0.0,
                    longitude = 0.0,
                    posterUserID = preference.uuid,
                    propertyAddress = "Barrie, Canada",
                    propertyAmount = price.toLong(),
                    propertyCategory = type,
                    propertyName = title,
                    propertySize = type,
                    imageUrls = listOf("")
                )
                viewModel.postRent(request)
            }
        }
    }

    override fun initObservers() {
        observePostRentResponse()
    }

    private fun observePostRentResponse() {
        viewModel.addPostResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    showProgress()
                }

                Status.COMPLETE -> {
                    hideProgress()
                    if (response.data?.isNotEmpty() == true) {
                        preference.uuid = response.data
                        showToast("Success", "Rent post Successful!", MotionToastStyle.SUCCESS)
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
        dialog = ProgressDialog.progressDialog(requireContext())
        dialog.show()
    }

    private fun hideProgress() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}