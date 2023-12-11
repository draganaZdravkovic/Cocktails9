package com.example.cocktails9.data.model

sealed class FavoritesItem {
    abstract fun getType(): Int

    enum class Type {
        COCKTAIL,
        CATEGORY
    }
}