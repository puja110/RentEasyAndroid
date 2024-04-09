package com.example.renteasyandroid.feature.main.landing.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseFragment
import com.example.renteasyandroid.databinding.FragmentProfileBinding
import com.example.renteasyandroid.feature.auth.login.LoginActivity
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.utils.Status
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.storage


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(requireContext())
    }

    override fun layout(): Int = R.layout.fragment_profile

    companion object {
        fun getInstance(): Fragment {
            return ProfileFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getUserDetail()

        binding.btnLogout.setOnClickListener {
            // Create a new user account with email and password
            try {
                FirebaseAuth.getInstance().signOut()
                LoginActivity.start(requireActivity())
            } catch (e: Exception) {
            }
        }

        binding.cvHeader.setOnClickListener {
            val i = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(i)
        }

        binding.cvSetting.setOnClickListener {
            val i = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(i)
        }
        binding.cvAboutUs.setOnClickListener {
            val i = Intent(requireContext(), AboutUsActivity::class.java)
            startActivity(i)
        }
    }

    override fun initObservers() {
        observeUserDetail()
    }

    private fun observeUserDetail() {
        viewModel.userDetailResponse.observe(viewLifecycleOwner) { response -> // Use viewLifecycleOwner in Fragments
            when (response.status) {
                Status.LOADING -> {
                    // Optionally handle loading state, e.g., show a progress indicator
                }

                Status.COMPLETE -> {
                    // Initialize Firebase Storage reference
                    val storage = Firebase.storage("gs://renteasy-7a973.appspot.com")
                    val childPath =
                        "profileImages/${FirebaseAuth.getInstance().currentUser?.uid ?: "atLHJvnU33cWRhr210smoxvnBEg2"}.jpg"
                    storage.reference.child(childPath).downloadUrl.addOnSuccessListener {
                        try {
                            // Use Glide to load the image from Firebase Storage
                            Glide.with(binding.root.context)
                                .load(it) // Load from Firebase Storage reference
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(binding.ivProfileImage) // Target ImageView
                        } catch (_: IllegalArgumentException) {

                        }
                    }

                    response.data?.let { userDetail ->
                        // Set text views with user details
                        binding.tvEmail.text = userDetail.email
                        binding.tvName.text = "${userDetail.firstName} ${userDetail.lastName}"
                    }
                }

                Status.ERROR -> {
                    // Optionally handle error state, e.g., show an error message
                }
            }
        }
    }

}