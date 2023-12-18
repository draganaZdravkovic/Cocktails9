package com.example.cocktails9.data.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("strAlcoholic")
    val alcoholic: String? = null,
    @SerializedName("strCategory")
    val category: String? = null,
    @SerializedName("strGlass")
    val glass: String? = null,
    @SerializedName("strIngredient1")
    val ingredient: String? = null
    ) : FavoritesItem() {
    override fun getType(): Int {
        return Type.CATEGORY.ordinal
    }
}
