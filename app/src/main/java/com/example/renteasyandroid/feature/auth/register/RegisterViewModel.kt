package com.example.renteasyandroid.feature.auth.register

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.renteasyandroid.feature.auth.data.AuthRepositoryImpl
import kotlinx.coroutines.cancel

class RegisterViewModel : ViewModel(),
    DefaultLifecycleObserver {

    private val repository by lazy { AuthRepositoryImpl.getInstance() }

    override fun onDestroy(owner: LifecycleOwner) {
        viewModelScope.cancel()
        super.onDestroy(owner)
    }
}