package com.example.cocktails9.ui.fragment.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktails9.data.model.Category
import com.example.cocktails9.data.model.Cocktails
import com.example.cocktails9.data.model.FavoritesItem
import com.example.cocktails9.data.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: FavoritesRepository) :
    ViewModel() {
    private val _getFavoritesList: MutableLiveData<List<FavoritesItem>> = MutableLiveData()
    val getFavoritesList: LiveData<List<FavoritesItem>> get() = _getFavoritesList

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

    fun getFavorites() {
        viewModelScope.launch {
            _getFavoritesList.value = sortCocktailsToCategories(
                repository.getFavorites()
            )
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