package com.example.renteasyandroid.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

class SearchActivity : BaseActivity<ActivitySearchBinding>() {

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.provideFactory(this)
    }

    private var adapter: SearchAdapter? = null
    val list: MutableList<SearchResponse> = mutableListOf()

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun layout() = R.layout.activity_search

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.imageArrowLeft.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.getSearchResponse()
        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })
    }

    override fun initObservers() {
        observeSearchResponse()
    }

    private fun observeSearchResponse() {
        viewModel.searchResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {

                }

                Status.COMPLETE -> {
                    response.data?.let {
                        list.clear()
                        list.addAll(it)
                        adapter = SearchAdapter(it.toMutableList()) { response ->
                            RentDetailActivity.start(
                                this,
                                response.image,
                                response.title,
                                response.address,
                                response.roomCount,
                                response.description,
                                response.owner,
                                response.price,
                                response.currency_code
                            )
                        }
                        binding.rvSearch.adapter = adapter
                    }
                }

                Status.ERROR -> {

                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun filter(text: String) {
        val newList: MutableList<SearchResponse> = mutableListOf()

        for (item in list) {
            if (item.title.lowercase(Locale.getDefault())
                    .contains(text.lowercase(Locale.getDefault()))
            ) {
                newList.add(item)
            }
        }
        if (newList.isEmpty()) {
            Toast.makeText(this, "No Data Found!", Toast.LENGTH_SHORT).show()
        } else {
            adapter?.updateData(newList)
            adapter?.notifyDataSetChanged()
        }
    }

}