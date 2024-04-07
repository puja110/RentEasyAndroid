package com.example.renteasyandroid.search

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
import com.example.renteasyandroid.search.data.SearchRepository
import com.example.renteasyandroid.search.data.SearchRepositoryImpl
import com.example.renteasyandroid.search.data.model.SearchResponse
import com.example.renteasyandroid.utils.Response
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel(),
    DefaultLifecycleObserver {
    companion object {
        fun provideFactory(
            context: Context,
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository by lazy { SearchRepositoryImpl.getInstance(context) }
                SearchViewModel(
                    repository = myRepository,
                )
            }
        }
    }

    private val searchUseCase = MutableLiveData<Response<List<SearchResponse>>>()
    val searchResponse: LiveData<Response<List<SearchResponse>>> =
        searchUseCase

    override fun onDestroy(owner: LifecycleOwner) {
        viewModelScope.cancel()
        super.onDestroy(owner)
    }

    fun getSearchResponse() {
        viewModelScope.launch {
            searchUseCase.value = Response.loading()
            try {
                searchUseCase.value = Response.complete(
                    repository.getSearchResponse()
                )
            } catch (error: Exception) {
                error.printStackTrace()
                searchUseCase.value = Response.error(error)
            }
        }
    }

}