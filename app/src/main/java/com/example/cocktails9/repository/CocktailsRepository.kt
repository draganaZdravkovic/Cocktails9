package com.example.cocktails9.repository

import com.example.cocktails9.api.ApiInterface
import com.example.cocktails9.model.Result
import retrofit2.Response
import javax.inject.Inject

class CocktailsRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getResult(searchQuery: String): Response<Result> {
        return apiInterface.getCocktails(searchQuery)
    }
}
