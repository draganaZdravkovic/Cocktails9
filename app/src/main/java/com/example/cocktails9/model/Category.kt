package com.example.cocktails9.model

data class Category(val category: String) : FavoritesItem() {
    override fun getType(): Int {
        return Type.CATEGORY.ordinal
    }
}
