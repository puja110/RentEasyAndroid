package com.example.renteasyandroid.feature.main.landing.detail

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.database.MainDatabase
import com.example.renteasyandroid.database.entity.UserRatingEntity
import com.example.renteasyandroid.databinding.ActivityRentDetailBinding
import com.example.renteasyandroid.feature.main.data.model.UserRatingResponseList
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.utils.PreferenceHelper
import com.example.renteasyandroid.utils.PreferenceHelper.setUsername
import com.example.renteasyandroid.utils.SharedPreferenceManager
import com.example.renteasyandroid.utils.Status
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

class RentDetailActivity : BaseActivity<ActivityRentDetailBinding>(), CoroutineScope {
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(this)
    }

    private val id: Int? by lazy {
        intent.getIntExtra(
            "id", 0
        )
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
    private var progressDialog: ProgressDialog? = null
    private lateinit var preference: SharedPreferenceManager

    companion object {
        fun start(
            activity: Activity,
            // id: String,
            image: String,
            title: String,
            address: String,
            roomCount: String,
            description: String,
            owner: String,
            price: String,
            countryCode: String,
        ) {
            val intent = Intent(activity, RentDetailActivity::class.java)
            // intent.putExtra("id", id)
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

        val database = MainDatabase.getInstance(this)
        val prefs = PreferenceHelper.customPreference(this)
        preference = SharedPreferenceManager(this)
        progressDialog = ProgressDialog(this)

        var sizeOfDatabase: Int
        launch {
            sizeOfDatabase = database?.getUserRatingDao()?.getSizeOfDatabaseById(id ?: -1) ?: 0
            if (sizeOfDatabase < 1) {
                UserRatingResponseList.getModel().forEach {
                    database?.getUserRatingDao()?.addUserRating(
                        UserRatingEntity(
                            propertyId = id,
                            username = it.username,
                            rating = it.rating.toFloat(),
                            description = it.review,
                            userImage = it.userImage
                        )
                    )
                }
            }
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

        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

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
            val llLayout = view.findViewById<LinearLayoutCompat>(R.id.llLayout)
            val eTReview = view.findViewById<AppCompatEditText>(R.id.etWrite)
            val rating = view.findViewById<RatingBar>(R.id.ratingBar)
            val ivDismiss = view.findViewById<Button>(R.id.btnCancel)
            val postRatingBtn = view.findViewById<Button>(R.id.btnDone)
            builder.setView(view)
            ivDismiss.setOnClickListener {
                builder.dismiss()
            }
            postRatingBtn.setOnClickListener {
                val review = eTReview.text.toString()
                if (review.isNotEmpty()) {
                    showProgress()
                    Handler(Looper.getMainLooper()).postDelayed({
                        hideProgress()
                         launch {
                            database?.getUserRatingDao()?.addUserRating(
                                UserRatingEntity(
                                    propertyId = id,
                                    username = if (prefs.setUsername?.isNotEmpty() == true) {
                                        prefs.setUsername
                                    } else {
                                        "John Doe"
                                    },
                                    rating = rating.rating,
                                    description = review,
                                    userImage = "https://images.pexels.com/photos/10425598/pexels-photo-10425598.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                                )
                            )
                         }
                        Toast.makeText(this, "Review added Successfully!", Toast.LENGTH_LONG).show()
                        builder.dismiss()
                    }, 1500)
                } else {
                    val snackbar = Snackbar
                        .make(
                            llLayout,
                            "Review field should not be empty!",
                            Snackbar.LENGTH_LONG
                        )
                    snackbar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
                    snackbar.show()
                }

            }
            builder.setCanceledOnTouchOutside(false)
            builder.show()
        }

        // Initializing recycler to list the user review
        val recyclerView = findViewById<RecyclerView>(R.id.rvRatings)
        database?.getUserRatingDao()?.getUserRatingsByDetailId(id ?: -1)?.observe(this) { result ->
            val adapter =
                UserRatingAdapter(result as ArrayList<UserRatingEntity>) {}
            recyclerView.adapter = adapter
        }
    }

    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun showProgress() {
        progressDialog?.setTitle("Please Wait")
        progressDialog?.setMessage("Loading ...")
        progressDialog?.show()
    }

    private fun hideProgress() {
        if (progressDialog != null) {
            progressDialog?.dismiss()
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
