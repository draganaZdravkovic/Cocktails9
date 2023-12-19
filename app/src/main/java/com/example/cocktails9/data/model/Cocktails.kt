package com.example.cocktails9.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites")
data class Cocktails(
    @PrimaryKey()
    @SerializedName("idDrink")
    val id: String,
    @SerializedName("strDrinkThumb")
    val image: String?,
    @SerializedName("strDrink")
    val name: String?,
    @SerializedName("strAlcoholic")
    var alcoholic: String?,
    var isFavorite: Boolean = false,
    var userEmail: String?
) : FavoritesItem() {
    override fun getType(): Int {
        return Type.COCKTAIL.ordinal
    }
}
