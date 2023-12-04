package com.example.cocktails9.repository

import com.example.cocktails9.api.ApiInterface
import com.example.cocktails9.model.Result
import retrofit2.Response

class CocktailsRepository(private val apiInterface: ApiInterface) {

    suspend fun getResult(): Response<Result> {
        return apiInterface.getCocktails()
    }
}