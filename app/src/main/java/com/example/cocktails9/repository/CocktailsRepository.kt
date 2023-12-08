package com.example.cocktails9.repository

import com.example.cocktails9.api.ApiInterface
import com.example.cocktails9.model.CocktailsResponse
import retrofit2.Response
import javax.inject.Inject

class CocktailsRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getCocktailsResponse(searchQuery: String): Response<CocktailsResponse> {
        return apiInterface.getCocktails(searchQuery)
    }
}
