package com.example.cocktails9.model

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading(val isLoading: Boolean) : Resource<Nothing>()
    data class Error(val message: String) : Resource<Nothing>()
}
