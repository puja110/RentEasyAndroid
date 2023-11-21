package com.example.renteasyandroid.feature.main.landing.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseFragment
import com.example.renteasyandroid.databinding.FragmentFavouritesBinding
import com.example.renteasyandroid.feature.main.landing.MainViewModel
import com.example.renteasyandroid.utils.Status

class FavouritesFragment : BaseFragment<FragmentFavouritesBinding>() {

    private val viewModel: MainViewModel by viewModels()

    private var adapter: FavouritesAdapter? = null
    override fun layout(): Int = R.layout.fragment_favourites

    companion object {
        fun getInstance(): Fragment {
            return FavouritesFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavouritesResponse()
    }

    override fun initObservers() {
        observeFavoritesResponse()
    }

    private fun observeFavoritesResponse() {
        viewModel.favouriteResponse.observe(this) { response ->
            when (response.status) {
                Status.LOADING -> {

                }

                Status.COMPLETE -> {
                    response.data?.let {
                        adapter = FavouritesAdapter(it.toMutableList()) { response -> }
                        binding.rvFavourites.adapter = adapter
                    }
                }

                Status.ERROR -> {

                }
            }
        }
    }
}