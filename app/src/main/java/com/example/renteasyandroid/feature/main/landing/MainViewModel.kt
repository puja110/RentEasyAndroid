package com.example.renteasyandroid.feature.main.landing

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.renteasyandroid.feature.main.data.MainRepositoryImpl
import kotlinx.coroutines.cancel

class MainViewModel : ViewModel(),
    DefaultLifecycleObserver {

    private val repository by lazy { MainRepositoryImpl.getInstance() }

    override fun onDestroy(owner: LifecycleOwner) {
        viewModelScope.cancel()
        super.onDestroy(owner)
    }
}