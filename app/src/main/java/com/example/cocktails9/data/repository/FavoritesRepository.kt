package com.example.cocktails9.data.repository

import com.example.cocktails9.data.local.FavoritesDao
import com.example.cocktails9.data.model.Cocktails
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val favoritesDao: FavoritesDao) {

    suspend fun getFavorites(): List<Cocktails> {
        return favoritesDao.getAllFavorites()
    }

    suspend fun insertFavorite(cocktail: Cocktails) {
        favoritesDao.insertFavorite(cocktail)
    }

    suspend fun removeFavorite(cocktail: Cocktails) {
        favoritesDao.deleteFavorite(cocktail)
    }
}