package com.example.cocktails9.data.repository

import com.example.cocktails9.data.model.CategoryResponse
import com.example.cocktails9.data.remote.ApiInterface
import retrofit2.Response
import javax.inject.Inject

class FilterRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getCategoryResponse(params: Map<String, String>): Response<CategoryResponse> {
        return apiInterface.getCategoryResponse(params)
    }
}