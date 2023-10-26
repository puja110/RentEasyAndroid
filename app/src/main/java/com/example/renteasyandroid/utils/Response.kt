package com.example.renteasyandroid.utils

data class Response<ResultType>(
    var status: Status,
    var data: ResultType? = null,
    var error: Throwable? = null
) {

    companion object {
        fun <ResultType> loading(message: ResultType? = null): Response<ResultType> {
            return Response(Status.LOADING, message, null)
        }

        fun <ResultType> complete(data: ResultType?): Response<ResultType> {
            return Response(Status.COMPLETE, data, null)
        }

        fun <ResultType> error(e: Throwable?): Response<ResultType> {
            return Response(Status.ERROR, null, e)
        }
    }
}