package com.example.cocktails9.data.remote

import retrofit2.Response
import com.example.cocktails9.data.model.CocktailsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("search.php")
    suspend fun getCocktails(@Query("s") searchQuery: String = ""): Response<CocktailsResponse>
}