package com.example.cocktails9.data.repository

import androidx.lifecycle.LiveData
import com.example.cocktails9.data.local.FavoritesDao
import com.example.cocktails9.data.model.Cocktails
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val favoritesDao: FavoritesDao) {

    fun getFavorites(userEmail: String): LiveData<List<Cocktails>> {
        return favoritesDao.getAllFavorites(userEmail)
    }

    fun getIsFavorite(cocktailID: String, userEmail: String): LiveData<Boolean> {
        return  favoritesDao.getIsFavorite(cocktailID, userEmail)
    }

    suspend fun getAllFavoritesId(userEmail: String): List<String> {
        return favoritesDao.getAllFavoritesId(userEmail)
    }
}