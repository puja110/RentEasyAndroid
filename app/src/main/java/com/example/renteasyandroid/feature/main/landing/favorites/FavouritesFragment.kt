package com.example.renteasyandroid.feature.main.landing.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseFragment
import com.example.renteasyandroid.databinding.FragmentFavouritesBinding

class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>() {

    override fun layout(): Int = R.layout.fragment_favourites

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun getInstance(): Fragment {
            return FavouritesFragment()
        }
    }

    override fun initObservers() {
    }
}