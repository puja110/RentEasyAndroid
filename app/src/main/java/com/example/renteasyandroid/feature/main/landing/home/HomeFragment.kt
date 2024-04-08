package com.example.renteasyandroid.feature.main.landing.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseFragment
import com.example.renteasyandroid.databinding.FragmentHomeBinding
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.feature.main.landing.detail.RentDetailActivity
import com.example.renteasyandroid.search.SearchActivity
import com.example.renteasyandroid.utils.Status

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(requireContext())
    }

    private var adapter: CategoriesAdapter? = null
    private var rAdapter: RecentlyUpdatedAdapter? = null

    override fun layout(): Int = R.layout.fragment_home

    companion object {
        fun getInstance(): Fragment {
            return HomeFragment()
        }

        private const val TAG = "HomeFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //gets categories list through viewmodel
        viewModel.getCategoriesResponse()

        //gets recently updated list from local database
        viewModel.getRecentlyUpdatedResponse()

        binding.includeSearch.constraintSearch.setOnClickListener {
            SearchActivity.start(requireActivity())
        }
    }

    override fun initObservers() {
        observeCategoryResponse()
        observeRecentlyUpdatedResponse()
    }

    //    observes Status with Status types Loading, Complete and Error
    //    Loading : to show the loading
    //    Complete : Called when success
    //    Error : called when there is an error
    private fun observeCategoryResponse() {
        viewModel.categoryResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {

                }

                Status.COMPLETE -> {
                    response.data?.let {
                        val layoutManager = LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        adapter = CategoriesAdapter(it.toMutableList()) { response -> }
                        binding.rvCategories.layoutManager = layoutManager
                        binding.rvCategories.adapter = adapter
                    }
                }

                Status.ERROR -> {

                }
            }
        }
    }

    //    observes Status with Status types Loading, Complete and Error
    //    Loading : to show the loading
    //    Complete : Called when success
    //    Error : called when there is an error
    private fun observeRecentlyUpdatedResponse() {
        viewModel.recentResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d(TAG, "observeRecentlyUpdatedResponse: ${Status.LOADING}")
                }

                Status.COMPLETE -> {
                    binding.progressBar.visibility = View.GONE
                    response.data?.let {
                        rAdapter = RecentlyUpdatedAdapter(it.toMutableList()) { response, favorite ->
                            Log.d(TAG, "observeRecentlyUpdatedResponse: ${response.propertyName}")
                            RentDetailActivity.start(
                                requireActivity(),
                                response.imageUrls?.get(0) ?: "",
                                response.propertyName ?: "",
                                response.propertyAddress ?: "",
                                response.propertySize ?: "",
                                response.description ?: "",
                                response.posterUserID ?: "",
                                response.propertyAmount.toString(),
                                "CA"
                            )
                        }
                        binding.rvRecentlyUpdated.adapter = rAdapter
                    }
                }

                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE

                }
            }
        }
    }
}