package com.example.renteasyandroid.feature.main.landing.add

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseFragment
import com.example.renteasyandroid.databinding.FragmentPostRentBinding
import com.example.renteasyandroid.feature.main.data.model.AddPostRequest
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.utils.ProgressDialog
import com.example.renteasyandroid.utils.SharedPreferenceManager
import com.example.renteasyandroid.utils.Status
import com.example.renteasyandroid.utils.showToast
import com.github.drjacky.imagepicker.ImagePicker
import www.sanju.motiontoast.MotionToastStyle

/**
 * Fragment for posting a rent listing.
 */
class PostRentFragment : BaseFragment<FragmentPostRentBinding>() {

    // View model for handling data operations
    private val viewModel: MainViewModel by viewModels {
        MainViewModel.provideFactory(requireContext())
    }

    // List to store selected camera URIs
    private var mCameraUri = mutableListOf<Uri>()
    private var adapter: PropertyImageAdapter? = null
    private lateinit var preference: SharedPreferenceManager
    private lateinit var dialog: Dialog

    // Define a constant variable for the notification channel ID
    private val CHANNEL_ID = "MY_CHANNEL"

    // Initialize a variable to set the unique identifier for notifications
    private var notificationId = 1

    // Activity result launcher for camera capture
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
        if (activityResult.resultCode == Activity.RESULT_OK) {
            val uri = activityResult.data?.data!!
            mCameraUri.add(uri)
            adapter?.updateData(mCameraUri)
            adapter?.notifyItemChanged(mCameraUri.size - 1)
        } else {
            parseError(activityResult)
        }
    }

    /* Function to handle parsing errors during activity result processing */
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
        preference = SharedPreferenceManager(requireContext())

        // Calling function to create notification channel
        createNotificationChannel()

        adapter = PropertyImageAdapter(mCameraUri, {})
        val layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvPropertyImage.layoutManager = layoutManager
        binding.rvPropertyImage.adapter = adapter

        // Button click to add property image
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

        // Button click to submit rent post
        binding.btnSubmit.setOnClickListener {
            handleNotification()

            val title = binding.etTitle.text.toString().trim()
            val type = binding.etType.text.toString().trim()
            val price = binding.etPrice.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val description = binding.etDescription.text.toString().trim()

            // Validate form fields
            if (title.isEmpty()) {
                showToast("Warning", "Title field cannot be empty!", MotionToastStyle.WARNING)
            } else if (type.isEmpty()) {
                showToast("Warning", "Type field cannot be empty!", MotionToastStyle.WARNING)
            } else if (price.isEmpty()) {
                showToast("Warning", "Price field cannot be empty!", MotionToastStyle.WARNING)
            } else if (address.isEmpty()) {
                showToast("Warning", "Address field cannot be empty!", MotionToastStyle.WARNING)
            } else if (description.isEmpty()) {
                showToast("Warning", "Description field cannot be empty!", MotionToastStyle.WARNING)
            } else {
                // Create request object and call ViewModel's postRent function
                val request = AddPostRequest(
                    description = description,
                    isBooked = false,
                    isNegotiable = binding.rbNegotiable.isChecked,
                    latitude = 44.39710332221014,
                    longitude = -79.69553627619078,
                    posterUserID = preference.uuid,
                    propertyAddress = address,
                    propertyAmount = price.toLong(),
                    propertyCategory = type,
                    propertyName = title,
                    propertySize = type,
                    imageUrls = listOf("https://images.pexels.com/photos/221540/pexels-photo-221540.jpeg?auto=compress&cs=tinysrgb&w=800")
                )
                viewModel.postRent(request)
            }
        }
    }

    /* Function to handle notification creation and display */
    private fun handleNotification() {
        // Creating notification builder instance
        var builder = NotificationCompat.Builder(requireActivity(), CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Check if the notification permission is granted
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Show alert dialog to manage permission
            showNotificationPermissionAlert();
        }

        val id = notificationId++

        // Build and display the notification
        val notificationManagerCompat = NotificationManagerCompat.from(requireActivity())
        notificationManagerCompat.notify(id, builder.build())
    }

    /* Function to create notification channel */
    private fun createNotificationChannel() {
        // Create the Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name_rent_easy)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Register the channel with the system
            val notificationManager: NotificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /* Function to show alert dialog for managing notification permission */
    private fun showNotificationPermissionAlert() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.alert_title)
        builder.setMessage(R.string.alert_message)
        builder.setPositiveButton(R.string.yes) { dialog, which ->
            // Allows the user to view and manage permissions and settings for the app
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts(getString(R.string.package_name), "com.example.renteasyandroid", null))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        builder.setNegativeButton(R.string.no) { dialog, which ->
            dialog.dismiss()
        }

        // Create and show the dialog
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun initObservers() {
        observePostRentResponse()
    }

    /* Function to observe post rent response from ViewModel */
    private fun observePostRentResponse() {
        viewModel.addPostResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {
                    showProgress()
                }

                Status.COMPLETE -> {
                    hideProgress()
                    if (response.data?.isNotEmpty() == true) {
                        preference.uuid = response.data
                        showToast("Success", "Rent post Successful!", MotionToastStyle.SUCCESS)
                    } else {
                        showToast("Error", response.data.toString(), MotionToastStyle.ERROR)
                    }
                }

                Status.ERROR -> {
                    hideProgress()
                    showToast("Error", response.error.toString(), MotionToastStyle.ERROR)
                }
            }
        }
    }

    /* Function to show progress dialog */
    private fun showProgress() {
        dialog = ProgressDialog.progressDialog(requireContext())
        dialog.show()
    }

    /* Function to hide progress dialog */
    private fun hideProgress() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
    }
}
