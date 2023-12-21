package com.example.cocktails9.data.remote

import com.example.cocktails9.data.model.CategoryResponse
import com.example.cocktails9.data.model.CocktailDetailsResponse
import retrofit2.Response
import com.example.cocktails9.data.model.CocktailsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiInterface {

    @GET("search.php")
    suspend fun getCocktails(@Query("s") searchQuery: String = ""): Response<CocktailsResponse>

    @GET("filter.php")
    suspend fun getCocktailsByCategory(@QueryMap params: Map<String, String>): Response<CocktailsResponse>

    @GET("lookup.php")
    suspend fun getCocktailDetails(@Query("i") cocktailID: String = ""): Response<CocktailDetailsResponse>

    @GET("list.php")
    suspend fun getCategoryResponse(@QueryMap params: Map<String, String>): Response<CategoryResponse>


}