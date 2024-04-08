package com.example.renteasyandroid.feature.main.landing.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseFragment
import com.example.renteasyandroid.databinding.FragmentProfileBinding
import com.example.renteasyandroid.feature.auth.login.LoginActivity
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.google.firebase.auth.FirebaseAuth


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
    }

    override fun initObservers() {
    }

}