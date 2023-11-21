package com.example.renteasyandroid.feature.main.landing.add

import android.app.Activity
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
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.utils.showToast
import com.github.drjacky.imagepicker.ImagePicker
import www.sanju.motiontoast.MotionToastStyle

class PostRentFragment : BaseFragment<FragmentPostRentBinding>() {

    private val viewModel: MainViewModel by viewModels()
    private var mCameraUri = mutableListOf<Uri>()
    private var adapter: PropertyImageAdapter? = null

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                mCameraUri.add(uri)
                adapter?.updateData(mCameraUri)
                adapter?.notifyItemChanged(mCameraUri.size-1)
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
    }

    override fun initObservers() {
    }

}