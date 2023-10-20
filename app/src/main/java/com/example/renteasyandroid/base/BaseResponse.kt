package com.example.renteasyandroid.base

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("message") @Expose var message: String? = null,
    @SerializedName("error") @Expose var status: Boolean? = false,
    @SerializedName("data") @Expose var response: T? = null
)