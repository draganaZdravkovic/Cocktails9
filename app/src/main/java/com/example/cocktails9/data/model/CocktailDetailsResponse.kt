package com.example.cocktails9.data.model

import com.google.gson.annotations.SerializedName

data class CocktailDetailsResponse(
    @SerializedName("drinks")
    val list: List<CocktailDetails>?
)
