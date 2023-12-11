package com.example.cocktails9.data.repository

import com.example.cocktails9.data.model.CocktailsResponse
import com.example.cocktails9.data.remote.ApiInterface
import retrofit2.Response
import javax.inject.Inject

class CocktailsRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getCocktailsResponse(searchQuery: String): Response<CocktailsResponse> {
        return apiInterface.getCocktails(searchQuery)
    }
}
