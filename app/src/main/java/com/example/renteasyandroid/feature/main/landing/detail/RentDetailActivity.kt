package com.example.renteasyandroid.feature.main.landing.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityRentDetailBinding
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.utils.Status

class RentDetailActivity : BaseActivity<ActivityRentDetailBinding>() {
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(this)
    }
    private val image: String? by lazy {
        intent.getStringExtra(
            "image"
        )
    }

    private val title: String? by lazy {
        intent.getStringExtra(
            "title"
        )
    }

    private val address: String? by lazy {
        intent.getStringExtra(
            "address"
        )
    }

    private val roomCount: String? by lazy {
        intent.getStringExtra(
            "room_count"
        )
    }

    private val description: String? by lazy {
        intent.getStringExtra(
            "description"
        )
    }

    private val owner: String? by lazy {
        intent.getStringExtra(
            "owner"
        )
    }

    private val price: String? by lazy {
        intent.getStringExtra(
            "price"
        )
    }

    private val countryCode: String? by lazy {
        intent.getStringExtra(
            "country_code"
        )
    }

    private var adapter: HomeFacilitiesAdapter? = null
    private var nAdapter: NearPublicFacilitiesAdapter? = null

    companion object {
        fun start(
            activity: Activity,
            image: String,
            title: String,
            address: String,
            roomCount: String,
            description: String,
            owner: String,
            price: String,
            countryCode: String
        ) {
            val intent = Intent(activity, RentDetailActivity::class.java)
            intent.putExtra("image", image)
            intent.putExtra("title", title)
            intent.putExtra("address", address)
            intent.putExtra("room_count", roomCount)
            intent.putExtra("description", description)
            intent.putExtra("owner", owner)
            intent.putExtra("price", price)
            intent.putExtra("country_code", countryCode)

            activity.startActivity(intent)
        }
    }

    override fun layout() = R.layout.activity_rent_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.tvTitle.text = title
        binding.tvLocation.text = address
        binding.tvRoomNumber.text = "$roomCount rooms"
        binding.tvName.text = owner
        binding.tvDescriptionMessage.text = description
        binding.tvPrice.text = "${price}${countryCode}/month"
        Glide.with(binding.root.context)
            .load(image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.logo).into(binding.ivDetail)

        viewModel.getHomeFacilitiesResponse()
        viewModel.getNearPublicFacilitiesResponse()

        binding.ivShare.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.putExtra(
                Intent.EXTRA_TEXT, "${image}\n\n${description}"
            )
            shareIntent.type = "text/plain"
            startActivity(Intent.createChooser(shareIntent, "Share"))
        }

        binding.etWrite.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .create()
            val view = layoutInflater.inflate(R.layout.dialog_ratings, null)
            val ivDismiss = view.findViewById<Button>(R.id.btnCancel)
            builder.setView(view)
            ivDismiss.setOnClickListener {
                builder.dismiss()
            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
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
