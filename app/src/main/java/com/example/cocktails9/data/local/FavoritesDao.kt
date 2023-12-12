package com.example.cocktails9.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.cocktails9.data.model.Cocktails

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): LiveData< List<Cocktails>>

    @Query("SELECT id FROM favorites")
    suspend fun getAllFavoritesId(): List<String>

    @Upsert
    suspend fun insertFavorite(favoriteCocktail: Cocktails)

    @Delete
    suspend fun deleteFavorite(favoriteCocktail: Cocktails)
}