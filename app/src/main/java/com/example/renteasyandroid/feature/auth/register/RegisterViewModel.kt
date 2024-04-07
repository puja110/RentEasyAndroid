package com.example.renteasyandroid.feature.auth.register

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
import com.example.renteasyandroid.database.entity.UserEntity
import com.example.renteasyandroid.feature.auth.data.AuthRepository
import com.example.renteasyandroid.feature.auth.data.AuthRepositoryImpl
import com.example.renteasyandroid.utils.Response
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class RegisterViewModel(
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
                RegisterViewModel(
                    repository = myRepository,
                )
            }
        }
    }

    //usecase and response defined to maintain status, as well as returning the preferred response
    private val registerUseCase = MutableLiveData<Response<Boolean>>()
    val registerResponse: LiveData<Response<Boolean>> =
        registerUseCase

    //cancels the scope of viewmodel if the viewmodel lifecycle gets destroyed
    override fun onDestroy(owner: LifecycleOwner) {
        viewModelScope.cancel()
        super.onDestroy(owner)
    }

    // Assuming you have already initialized Firebase in your app

    // Function to create a user and handle registration
    fun createUser(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        password: String
    ) {
        viewModelScope.launch {
            registerUseCase.value = Response.loading()
            try {
                val entity = UserEntity(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    phoneNumber = phoneNumber,
                    password = password
                )

                registerUseCase.value = Response.complete(repository.insert(entity))
            } catch (error: Exception) {
                error.printStackTrace()
                registerUseCase.value = Response.error(error)
            }
        }
    }

}