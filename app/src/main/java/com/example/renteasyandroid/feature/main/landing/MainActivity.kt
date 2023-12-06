package com.example.renteasyandroid.feature.main.landing

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.fragment.app.Fragment
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseActivity
import com.example.renteasyandroid.databinding.ActivityMainBinding
import com.example.renteasyandroid.feature.main.landing.add.PostRentFragment
import com.example.renteasyandroid.feature.main.landing.favorites.FavouritesFragment
import com.example.renteasyandroid.feature.main.landing.home.HomeFragment
import com.example.renteasyandroid.feature.main.landing.profile.ProfileFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            activity.startActivity(intent)
            activity.finish()
        }
    }

    override fun layout() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDefaultFragment(savedInstanceState)

//      initializing bottom navigation view and its click listener
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    val fragment = HomeFragment.getInstance()
                    addFragment(fragment)
                    return@setOnItemSelectedListener true
                }

                R.id.action_favourite -> {
                    val fragment = FavouritesFragment.getInstance()
                    addFragment(fragment)
                    return@setOnItemSelectedListener true
                }

                R.id.action_add -> {
                    val fragment = PostRentFragment.getInstance()
                    addFragment(fragment)
                    return@setOnItemSelectedListener true
                }

                R.id.action_profile -> {
                    val fragment = ProfileFragment.getInstance()
                    addFragment(fragment)
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

    override fun initObservers() {
    }


    //  sets  Home Screen as the default fragment
    private fun setDefaultFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.main_container,
                    HomeFragment.getInstance()
                ).commit()
        }
    }

    // custom function to add fragment to the activity container that needs to be shown
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment, fragment.javaClass.simpleName)
            .commit()
    }

}