package com.example.cocktails9.data.repository

import androidx.lifecycle.LiveData
import com.example.cocktails9.data.local.FavoritesDao
import com.example.cocktails9.data.model.Cocktails
import javax.inject.Inject

class FavoritesRepository @Inject constructor(private val favoritesDao: FavoritesDao) {

    fun getFavorites(): LiveData<List<Cocktails>> {
        return favoritesDao.getAllFavorites()
    }

    suspend fun getAllFavoritesId(): List<String> {
        return favoritesDao.getAllFavoritesId()
    }
}