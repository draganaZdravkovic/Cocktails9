package com.example.cocktails9.data.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("drinks")
    val list: List<Category>?
)
