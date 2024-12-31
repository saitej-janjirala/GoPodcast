package com.saitejajanjirala.gopodcast.domain.util

sealed class Result<T>{
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
    data class  Loading<T>(val isLoading : Boolean = false) : Result<T>()
}