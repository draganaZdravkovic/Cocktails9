package com.example.cocktails9.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.cocktails9.data.model.Cocktails

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    suspend fun getAllFavorites(): List<Cocktails>

    @Upsert
    suspend fun insertFavorite(favoriteCocktail: Cocktails)

    @Delete
    suspend fun deleteFavorite(favoriteCocktail: Cocktails)
}