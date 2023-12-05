package com.example.cocktails9.api

import retrofit2.Response
import com.example.cocktails9.model.Result
import retrofit2.http.GET

interface ApiInterface {

    @GET("search.php?s=margarita")
    suspend fun getCocktails(): Response<Result>
}