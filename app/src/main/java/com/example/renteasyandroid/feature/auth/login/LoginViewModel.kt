package com.example.renteasyandroid.feature.auth.login

import android.content.Context
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.renteasyandroid.feature.auth.data.AuthRepository
import com.example.renteasyandroid.feature.auth.data.AuthRepositoryImpl
import com.example.renteasyandroid.utils.Response
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel(),
    DefaultLifecycleObserver {
    companion object {
        //provider for a viewmodel factory with arguments
        fun provideFactory(
            context: Context,
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository by lazy { AuthRepositoryImpl.getInstance(context) }
                LoginViewModel(
                    repository = myRepository,
                )
            }
        }
    }

    //usecase and response defined to maintain status, as well as returning the preferred response
    private val loginUseCase = MutableLiveData<Response<String>>()
    val loginResponse: LiveData<Response<String>> =
        loginUseCase

    //cancels the scope of viewmodel if the viewmodel lifecycle gets destroyed

    override fun onDestroy(owner: LifecycleOwner) {
        viewModelScope.cancel()
        super.onDestroy(owner)
    }

    //function to authenticate registered user and get the specific response after success
    fun authenticateUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            loginUseCase.value = Response.loading()
            try {
                loginUseCase.value = Response.complete(
                    repository.authenticateUser(email, password)
                )
            } catch (error: Exception) {
                error.printStackTrace()
                loginUseCase.value = Response.error(error)
            }
        }
    }
}