package com.example.renteasyandroid.feature.main.landing.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.renteasyandroid.R
import com.example.renteasyandroid.base.BaseFragment
import com.example.renteasyandroid.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private var adapter: CategoriesAdapter? = null

    override fun layout(): Int = R.layout.fragment_home
    override fun initViewModel() {
    }

    companion object {
       fun getInstance(): Fragment {
           return HomeFragment()
       }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initObservers() {
    }
}