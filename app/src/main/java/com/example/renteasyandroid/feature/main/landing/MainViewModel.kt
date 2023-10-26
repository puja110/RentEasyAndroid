package com.example.renteasyandroid.feature.main.landing

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.renteasyandroid.feature.main.data.MainRepositoryImpl
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.utils.Response
import kotlinx.coroutines.cancel

class MainViewModel : ViewModel(),
    DefaultLifecycleObserver {

    private val repository by lazy { MainRepositoryImpl.getInstance() }

    override fun onDestroy(owner: LifecycleOwner) {
        viewModelScope.cancel()
        super.onDestroy(owner)
    }
}