package com.example.cocktails9.ui.fragment.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.cocktails9.data.model.Category
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.data.model.FavoritesItem
import com.example.cocktails9.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.set

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository) :
    ViewModel() {
    val favoritesListLiveData = repository.getFavorites().map {
        sortCocktailsToCategories(it)
    }

    fun addFavorite(cocktail: Cocktails) {
        viewModelScope.launch {
            repository.insertFavorite(cocktail)
        }
    }

    fun removeFavorite(cocktail: Cocktails) {
        viewModelScope.launch {
            repository.removeFavorite(cocktail)
        }
    }

    private fun sortCocktailsToCategories(favorites: List<Cocktails>): List<FavoritesItem> {
        val categoryMap: MutableMap<String, MutableList<Cocktails>> = mutableMapOf()

        for (cocktail in favorites) {
            val category = cocktail.category ?: ""
            if (!categoryMap.containsKey(category)) {
                categoryMap[category] = mutableListOf()
            }
            categoryMap[category]?.add(cocktail)
        }

        val favItems: MutableList<FavoritesItem> = mutableListOf()
        for ((category, cocktailsInCategory) in categoryMap) {
            favItems.add(Category(category = category))
            favItems.addAll(cocktailsInCategory)
        }

        return favItems
    }
}