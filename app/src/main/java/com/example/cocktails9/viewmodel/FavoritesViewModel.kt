package com.example.cocktails9.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cocktails9.model.Category
import com.example.cocktails9.model.Cocktails
import com.example.cocktails9.model.FavoritesItem

class FavoritesViewModel() : ViewModel() {
    private val _getFavoritesList: MutableLiveData<List<FavoritesItem>> = MutableLiveData()
    val getFavoritesList: LiveData<List<FavoritesItem>> get() = _getFavoritesList

    fun getFavorites() {
        _getFavoritesList.value = mutableListOf(
            Category("Alcoholic"),
            Cocktails(
                "1",
                "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
                "Margarita",
                "Alcoholic"
            ),
            Cocktails(
                "2",
                "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
                "Margarita",
                "Alcoholic"
            ),
            Cocktails(
                "3",
                "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
                "Margarita",
                "Non-Alcoholic"
            ),
            Category("Non-Alcoholic"),
            Cocktails(
                "1",
                "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
                "Margarita",
                "Alcoholic"
            ),
            Cocktails(
                "2",
                "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
                "Margarita",
                "Alcoholic"
            ),
            Cocktails(
                "3",
                "https://www.thecocktaildb.com/images/media/drink/5noda61589575158.jpg",
                "Margarita",
                "Non-Alcoholic"
            )
        )
    }
}