package com.example.renteasyandroid.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivitySearchBinding
import com.example.renteasyandroid.feature.main.landing.detail.RentDetailActivity
import com.example.renteasyandroid.search.adapter.SearchAdapter
import com.example.renteasyandroid.search.data.model.SearchResponse
import com.example.renteasyandroid.utils.Status
import java.util.Locale

/**
 * Activity to perform property search.
 */
class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    // View model for handling search operations
    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.provideFactory(this)
    }

    // Adapter for displaying search results
    private var adapter: SearchAdapter? = null

    // List to store search results
    private val list: MutableList<SearchResponse> = mutableListOf()

    companion object {
        /**
         * Start the SearchActivity from an activity.
         * @param activity The activity from which to start the SearchActivity.
         */
        fun start(activity: Activity) {
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun layout() = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle back arrow click to finish the activity
        binding.imageArrowLeft.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // Trigger search operation when the query text changes
        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Filter the recycler view based on the new query text
                filter(newText)
                return false
            }
        })

        // Get search response from the view model
        viewModel.getSearchResponse()
    }

    override fun initObservers() {
        // Observe search response from the view model
        observeSearchResponse()
    }

    /* Function to observe search response */
    private fun observeSearchResponse() {
        viewModel.searchResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    // Show progress bar when loading data
                    binding.progressBar.visibility = View.VISIBLE
                }

                Status.COMPLETE -> {
                    // Hide progress bar and update UI with search results
                    binding.progressBar.visibility = View.GONE
                    response.data?.let {
                        list.clear()
                        list.addAll(it)
                        // Initialize and set up the adapter with search results
                        adapter = SearchAdapter(it.toMutableList()) { response ->
                            // Start RentDetailActivity with selected item details
                            RentDetailActivity.start(
                                this,
                                response.imageUrls?.get(0) ?: "",
                                response.propertyName ?: "",
                                response.propertyAddress ?: "",
                                response.propertySize ?: "",
                                response.description ?: "",
                                response.posterUserID ?: "",
                                response.propertyAmount.toString(),
                                "$",
                                response.latitude!!,
                                response.longitude!!
                            )
                        }
                        binding.rvSearch.adapter = adapter
                    }
                }

                Status.ERROR -> {
                    // Hide progress bar on error
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    /* Function to filter search results based on query text */
    private fun filter(text: String) {
        val newList: MutableList<SearchResponse> = mutableListOf()

        for (item in list) {
            if (item.propertyName!!.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                newList.add(item)
            }
        }
        // Show or hide status text based on filtered list
        if (newList.isEmpty()) {
            binding.tvStatus.visibility = View.VISIBLE
        } else {
            binding.tvStatus.visibility = View.GONE
        }
        // Update adapter data and notify changes
        adapter?.updateData(newList)
        adapter?.notifyDataSetChanged()
    }
}
