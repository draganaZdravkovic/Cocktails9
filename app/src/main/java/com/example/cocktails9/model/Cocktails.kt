package com.example.cocktails9.model

import com.google.gson.annotations.SerializedName

data class Cocktails(
    @SerializedName("idDrink")
    val id: String?,
    @SerializedName("strDrinkThumb")
    val image: String?,
    @SerializedName("strDrink")
    val name: String?,
    @SerializedName("strAlcoholic")
    val alcoholicFlag: String?
) : FavoritesItem() {
    override fun getType(): Int {
        return Type.COCKTAIL.ordinal
    }
}
