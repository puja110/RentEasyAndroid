package com.example.renteasyandroid.utils

import com.example.renteasyandroid.base.BaseResponse


inline fun <reified T> notNullMapper(baseResponse: BaseResponse<T>): T {
    return if (baseResponse.status == false) {
        val item = baseResponse.response
        item?.let {
            return@let it
        }.orElse {
            throw FailedResponseException(baseResponse.status!!, baseResponse.message.toString())
        }
    } else {
        throw FailedResponseException(baseResponse.status!!, baseResponse.message.toString())
    }
}