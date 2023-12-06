package com.example.cocktails9.api

import retrofit2.Response
import com.example.cocktails9.model.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("search.php")
    suspend fun getCocktails(@Query("s") searchQuery: String = ""): Response<Result>
}