package com.example.renteasyandroid.feature.main.landing.detail

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityRentDetailBinding
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.utils.Status

class RentDetailActivity : BaseActivity<ActivityRentDetailBinding>() {
    private val viewModel: MainViewModel by viewModels()
    private var adapter: HomeFacilitiesAdapter? = null
    private var nAdapter: NearPublicFacilitiesAdapter? = null

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, RentDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun layout() = R.layout.activity_rent_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
        Glide.with(binding.root.context)
            .load("https://fastly.picsum.photos/id/20/3670/2462.jpg?hmac=CmQ0ln-k5ZqkdtLvVO23LjVAEabZQx2wOaT4pyeG10I")
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo).into(binding.ivDetail)
        viewModel.getHomeFacilitiesResponse()
        viewModel.getNearPublicFacilitiesResponse()

        binding.ivShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            val uriToImage = Uri.parse("https://fastly.picsum.photos/id/19/2500/1667.jpg?hmac=7epGozH4QjToGaBf_xb2HbFTXoV5o8n_cYzB7I4lt6g")
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Convenient, Free & Easy To Use List of Best Accommodation finding Android Application. This is a beautiful and cozy home which would be perfect for those who are searching for small yet environment friendly place.\\n\\nThis home is  equipped with Washing Machine, Electric Stove, Microwave, Refrigerator, Cutlery.")
            shareIntent.putExtra(Intent.EXTRA_STREAM, uriToImage)
            shareIntent.type = "*/*"
            startActivity(Intent.createChooser(shareIntent, "Share"))

        }
    }

    override fun initObservers() {
        observeHomeFacilitiesResponse()
        observeNearPublicFacilitiesResponse()
    }

    private fun observeHomeFacilitiesResponse() {
        viewModel.homeFacilitiesResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {

                }

                Status.COMPLETE -> {
                    response.data?.let {
                        adapter = HomeFacilitiesAdapter(it.toMutableList())
                        binding.rvHomeFacilities.adapter = adapter
                    }
                }

                Status.ERROR -> {

                }
            }
        }
    }

    private fun observeNearPublicFacilitiesResponse() {
        viewModel.nearPublicFacilitiesResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {

                }

                Status.COMPLETE -> {
                    response.data?.let {
                        nAdapter = NearPublicFacilitiesAdapter(it.toMutableList())
                        binding.rvNearestFacilities.adapter = nAdapter
                    }
                }

                Status.ERROR -> {

                }
            }
        }
    }
}
