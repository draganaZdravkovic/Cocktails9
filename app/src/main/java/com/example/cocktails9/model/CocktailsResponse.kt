package com.example.cocktails9.model

import com.google.gson.annotations.SerializedName

data class CocktailsResponse(
    @SerializedName("drinks")
    val list: List<Cocktails>?
)
