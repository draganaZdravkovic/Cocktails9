package com.example.cocktails9.data.model

import com.google.gson.annotations.SerializedName

data class CocktailDetails(
    @SerializedName("idDrink")
    val id: String,
    @SerializedName("strDrinkThumb")
    val image: String?,
    @SerializedName("strDrink")
    val name: String?,
    @SerializedName("strAlcoholic")
    var alcoholic: String?,
    @SerializedName("strCategory")
    var category: String?,
    @SerializedName("strGlass")
    var glass: String?,
    @SerializedName("strInstructions")
    var instructions: String?,

    @SerializedName("strIngredient1")
    var ingredient1: String?,
    @SerializedName("strIngredient2")
    var ingredient2: String?,
    @SerializedName("strIngredient3")
    var ingredient3: String?,
    @SerializedName("strIngredient4")
    var ingredient4: String?,
    @SerializedName("strIngredient5")
    var ingredient5: String?,
    @SerializedName("strIngredient6")
    var ingredient6: String?,
    @SerializedName("strIngredient7")
    var ingredient7: String?,
    @SerializedName("strIngredient8")
    var ingredient8: String?,
    @SerializedName("strIngredient9")
    var ingredient9: String?,
    @SerializedName("strIngredient10")
    var ingredient10: String?,
    @SerializedName("strIngredient11")
    var ingredient11: String?,
    @SerializedName("strIngredient12")
    var ingredient12: String?,
    @SerializedName("strIngredient13")
    var ingredient13: String?,
    @SerializedName("strIngredient14")
    var ingredient14: String?,
    @SerializedName("strIngredient15")
    var ingredient15: String?,

    @SerializedName("strMeasure1")
    var measure1: String?,
    @SerializedName("strMeasure2")
    var measure2: String?,
    @SerializedName("strMeasure3")
    var measure3: String?,
    @SerializedName("strMeasure4")
    var measure4: String?,
    @SerializedName("strMeasure5")
    var measure5: String?,
    @SerializedName("strMeasure6")
    var measure6: String?,
    @SerializedName("strMeasure7")
    var measure7: String?,
    @SerializedName("strMeasure8")
    var measure8: String?,
    @SerializedName("strMeasure9")
    var measure9: String?,
    @SerializedName("strMeasure10")
    var measure10: String?,
    @SerializedName("strMeasure11")
    var measure11: String?,
    @SerializedName("strMeasure12")
    var measure12: String?,
    @SerializedName("strMeasure13")
    var measure13: String?,
    @SerializedName("strMeasure14")
    var measure14: String?,
    @SerializedName("strMeasure15")
    var measure15: String?,

    var ingredientMeasureString: String?,
)
