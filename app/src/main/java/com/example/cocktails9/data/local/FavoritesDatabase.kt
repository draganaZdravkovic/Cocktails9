package com.example.cocktails9.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cocktails9.data.model.Cocktails

@Database(entities = [Cocktails::class], version = 1, exportSchema = false)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao
}