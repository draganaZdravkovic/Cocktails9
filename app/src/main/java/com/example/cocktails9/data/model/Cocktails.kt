package com.example.cocktails9.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites", primaryKeys = ["id", "userEmail"])
data class Cocktails(
    @SerializedName("idDrink")
    val id: String,
    var userEmail: String,
    @SerializedName("strDrinkThumb")
    val image: String?,
    @SerializedName("strDrink")
    val name: String?,
    @SerializedName("strAlcoholic")
    var alcoholic: String?,
    var isFavorite: Boolean = false
) : FavoritesItem() {
    override fun getType(): Int {
        return Type.COCKTAIL.ordinal
    }
}
