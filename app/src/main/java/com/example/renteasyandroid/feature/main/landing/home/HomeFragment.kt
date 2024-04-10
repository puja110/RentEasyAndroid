package com.example.renteasyandroid.feature.main.landing.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseFragment
import com.example.renteasyandroid.databinding.FragmentHomeBinding
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.feature.main.landing.detail.RentDetailActivity
import com.example.renteasyandroid.feature.main.landing.map.MapActivity
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

        //gets categories list through view model
        viewModel.getCategoriesResponse()

        //gets recently updated list from local database
        viewModel.getRecentlyUpdatedResponse()

        binding.includeSearch.constraintSearch.setOnClickListener {
            SearchActivity.start(requireActivity())
        }

        binding.includeToolbar.ivLocation.setOnClickListener {
            val i = Intent(requireContext(), MapActivity::class.java)
            startActivity(i)
        }
    }

    override fun initObservers() {
        observeCategoryResponse()
        observeRecentlyUpdatedResponse()
        observeFavoriteStatusUpdate()
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
                        rAdapter = RecentlyUpdatedAdapter(it.toMutableList()) { response, isFavoriteClick ->
                            Log.d(TAG, "observeRecentlyUpdatedResponse: ${response.propertyName}")
                            if(isFavoriteClick) {
                                if (response.isFavourite == null || response.isFavourite != true) {
                                    response.id?.let { it1 ->
                                        viewModel.setFavorite(it1, false)
                                        response.isFavourite = true
                                    }
                                } else {
                                    response.id?.let { it1 ->
                                        viewModel.setFavorite(it1, true)
                                        response.isFavourite = false
                                    }
                                }
                            } else {
                                RentDetailActivity.start(
                                    requireActivity(),
                                    response.imageUrls?.get(0) ?: "",
                                    response.propertyName ?: "",
                                    response.propertyAddress ?: "",
                                    response.propertySize ?: "",
                                    response.description ?: "",
                                    response.posterUserID ?: "",
                                    response.propertyAmount.toString(),
                                    "$",
                                    response.latitude!!,
                                    response.longitude!!,
                                )
                            }
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

    private fun observeFavoriteStatusUpdate() {
        viewModel.favoritesUpdateStatus.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.LOADING -> {
                    // Show loading indicator
                }
                Status.COMPLETE -> {
                    Toast.makeText(context, "Favorite updated successfully", Toast.LENGTH_SHORT)
                        .show()
                    // Update UI to reflect the favorite has been added
                }
                Status.ERROR -> {
                    Toast.makeText(
                        context,
                        "Failed to update favorite: ${response.error?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    // Optionally, update UI to reflect the failure
                }
            }
        }
    }
}