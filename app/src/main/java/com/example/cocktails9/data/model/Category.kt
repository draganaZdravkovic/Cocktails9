package com.example.cocktails9.data.model

data class Category(val category: String) : FavoritesItem() {
    override fun getType(): Int {
        return Type.CATEGORY.ordinal
    }
}
