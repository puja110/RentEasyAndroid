package com.example.renteasyandroid.utils

import java.io.IOException

class FailedResponseException(val status: Boolean, override val message: String) :
    IOException(message)

class ValidationException(val status: Boolean, override val message: String) :
    IOException(message)

class ServerException(val status: Boolean, override val message: String) :
    IOException(message)

class FileNotFoundException(val status: Boolean, override val message: String) :
    IOException(message)

class NetworkNotAvailableException(errorMessage: String) : IOException(errorMessage)

class DataNotAvailableException : IOException()