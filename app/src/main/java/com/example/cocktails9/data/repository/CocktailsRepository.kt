package com.example.cocktails9.data.repository

import com.example.cocktails9.data.local.FavoritesDao
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.data.model.CocktailsResponse
import com.example.cocktails9.data.remote.ApiInterface
import retrofit2.Response
import javax.inject.Inject

class CocktailsRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val favoritesDao: FavoritesDao
) {

    suspend fun getCocktailsResponse(searchQuery: String): Response<CocktailsResponse> {
        return apiInterface.getCocktails(searchQuery)
    }

    suspend fun getCocktailsByCategory(params: Map<String, String>): Response<CocktailsResponse> {
        return apiInterface.getCocktailsByCategory(params)
    }

    suspend fun insertFavorite(cocktail: Cocktails) {
        favoritesDao.insertFavorite(cocktail)
    }

    suspend fun removeFavorite(cocktail: Cocktails) {
        favoritesDao.deleteFavorite(cocktail)
    }
}
