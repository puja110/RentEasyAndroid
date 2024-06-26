package com.example.renteasyandroid.feature.main.landing

import android.content.Context
import android.util.Log
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
import com.example.renteasyandroid.feature.main.data.model.AddPostRequest
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse
import com.example.renteasyandroid.feature.main.data.model.HomeFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.NearPublicFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.feature.main.data.model.UserDetail
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

        private const val TAG = "MainViewModel"
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

    private val addPostUseCase = MutableLiveData<Response<String>>()
    val addPostResponse: LiveData<Response<String>> =
        addPostUseCase

    private val _favoritesUpdateStatus = MutableLiveData<Response<Boolean>>()
    val favoritesUpdateStatus: LiveData<Response<Boolean>> = _favoritesUpdateStatus

    private val userDetailUseCase = MutableLiveData<Response<UserDetail>>()
    val userDetailResponse: LiveData<Response<UserDetail>> = userDetailUseCase

    private val _setUserDetailStatus = MutableLiveData<Response<Boolean>>()
    val setUserDetailStatus: LiveData<Response<Boolean>> = _setUserDetailStatus

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
                Log.d(TAG, "getRecentlyUpdatedResponse: ${repository.getRecentlyUpdatedResponse()}")
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

    fun postRent(
        request: AddPostRequest
    ) {
        viewModelScope.launch {
            addPostUseCase.value = Response.loading()
            try {
                addPostUseCase.value = Response.complete(repository.postRent(request))

            } catch (error: Exception) {
                error.printStackTrace()
                addPostUseCase.value = Response.error(error)
            }
        }
    }

    fun setFavorite(propertyId: String, remove: Boolean) {
        viewModelScope.launch {
            _favoritesUpdateStatus.value = Response.loading()
            try {
                val result = repository.setFavorites(propertyId, remove)
                if (result) {
                    _favoritesUpdateStatus.value = Response.complete(result)
                    getFavouritesResponse()
                    getRecentlyUpdatedResponse()
                } else {
                    _favoritesUpdateStatus.value =
                        Response.error(Throwable("Failed to update favorites"))
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error updating favorites", e)
                _favoritesUpdateStatus.value = Response.error(e)
            }
        }
    }


    fun setUserDetail(user: UserDetail) {
        viewModelScope.launch {
            _favoritesUpdateStatus.value = Response.loading()
            try {
                val result = repository.updateUserDetail(user)
                if (result) {
                    _setUserDetailStatus.value = Response.complete(result)
                    getUserDetail()
                } else {
                    _setUserDetailStatus.value =
                        Response.error(Throwable("Failed to update user detail"))
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error updating user", e)
                _setUserDetailStatus.value = Response.error(e)
            }
        }
    }

    fun getUserDetail() {
        viewModelScope.launch {
            userDetailUseCase.value = Response.loading()
            try {
                val userDetail = repository.getUserDetail()
                userDetailUseCase.value = Response.complete(userDetail)
            } catch (e: Exception) {
                Log.w(TAG, "Error fetching user details", e)
                userDetailUseCase.value = Response.error(e)
            }
        }
    }
}