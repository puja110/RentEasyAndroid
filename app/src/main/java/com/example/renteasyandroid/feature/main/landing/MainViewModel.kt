package com.example.renteasyandroid.feature.main.landing

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
import com.example.renteasyandroid.feature.main.data.MainRepository
import com.example.renteasyandroid.feature.main.data.MainRepositoryImpl
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse
import com.example.renteasyandroid.feature.main.data.model.HomeFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.NearPublicFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.utils.Response
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: MainRepository
) : ViewModel(),
    DefaultLifecycleObserver {
    companion object {
        fun provideFactory(
            context: Context,
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository by lazy { MainRepositoryImpl.getInstance(context) }
                MainViewModel(
                    repository = myRepository,
                )
            }
        }
    }

    private val categoryUseCase = MutableLiveData<Response<List<CategoryResponse>>>()
    val categoryResponse: LiveData<Response<List<CategoryResponse>>> =
        categoryUseCase

    private val recentUseCase = MutableLiveData<Response<List<RecentlyUpdatedResponse>>>()
    val recentResponse: LiveData<Response<List<RecentlyUpdatedResponse>>> =
        recentUseCase

    private val favouriteUseCase = MutableLiveData<Response<List<FavouritesResponse>>>()
    val favouriteResponse: LiveData<Response<List<FavouritesResponse>>> =
        favouriteUseCase

    private val homeFacilitiesUseCase = MutableLiveData<Response<List<HomeFacilitiesResponse>>>()
    val homeFacilitiesResponse: LiveData<Response<List<HomeFacilitiesResponse>>> =
        homeFacilitiesUseCase

    private val nearPublicFacilitiesUseCase =
        MutableLiveData<Response<List<NearPublicFacilitiesResponse>>>()
    val nearPublicFacilitiesResponse: LiveData<Response<List<NearPublicFacilitiesResponse>>> =
        nearPublicFacilitiesUseCase

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
                repository.saveRecentlyUpdatedResponse()
                recentUseCase.value = Response.complete(
                    repository.getRecentlyUpdatedResponse()
                )
            } catch (error: Exception) {
                error.printStackTrace()
                recentUseCase.value = Response.error(error)
            }
        }
    }

    fun getFavouritesResponse() {
        viewModelScope.launch {
            favouriteUseCase.value = Response.loading()
            try {
                favouriteUseCase.value = Response.complete(
                    repository.getFavouritesResponse()
                )
            } catch (error: Exception) {
                error.printStackTrace()
                favouriteUseCase.value = Response.error(error)
            }
        }
    }

    fun getHomeFacilitiesResponse() {
        viewModelScope.launch {
            homeFacilitiesUseCase.value = Response.loading()
            try {
                homeFacilitiesUseCase.value = Response.complete(
                    repository.getHomeFacilitiesResponse()
                )
            } catch (error: Exception) {
                error.printStackTrace()
                homeFacilitiesUseCase.value = Response.error(error)
            }
        }
    }

    fun getNearPublicFacilitiesResponse() {
        viewModelScope.launch {
            nearPublicFacilitiesUseCase.value = Response.loading()
            try {
                nearPublicFacilitiesUseCase.value = Response.complete(
                    repository.getNearPublicFacilitiesResponse()
                )
            } catch (error: Exception) {
                error.printStackTrace()
                nearPublicFacilitiesUseCase.value = Response.error(error)
            }
        }
    }
}