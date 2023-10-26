package com.example.renteasyandroid.feature.main.landing

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.renteasyandroid.feature.main.data.MainRepositoryImpl
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.utils.Response
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel : ViewModel(),
    DefaultLifecycleObserver {

    private val repository by lazy { MainRepositoryImpl.getInstance() }

    private val categoryUseCase = MutableLiveData<Response<List<CategoryResponse>>>()
    val categoryResponse: LiveData<Response<List<CategoryResponse>>> =
        categoryUseCase

    private val recentUseCase = MutableLiveData<Response<List<RecentlyUpdatedResponse>>>()
    val recentResponse: LiveData<Response<List<RecentlyUpdatedResponse>>> =
        recentUseCase

    override fun onDestroy(owner: LifecycleOwner) {
        viewModelScope.cancel()
        super.onDestroy(owner)
    }

    fun getCategoriesResponse() {
        viewModelScope.launch {
            categoryUseCase.value = Response.loading()
            try {
                categoryUseCase.value = Response.complete(
                    repository.getCategories()
                )
            } catch (error: Exception) {
                error.printStackTrace()
                categoryUseCase.value = Response.error(error)
            }
        }
    }

    fun getRecentlyUpdatedResponse() {
        viewModelScope.launch {
            recentUseCase.value = Response.loading()
            try {
                recentUseCase.value = Response.complete(
                    repository.getRecentlyUpdatedResponse()
                )
            } catch (error: Exception) {
                error.printStackTrace()
                recentUseCase.value = Response.error(error)
            }
        }
    }
}